package cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ProcessUtil {

    private final static int BUFFER_SIZE=2048;
    private final static String DEFAULT_ENCODING="gbk";
    private static class ProcessWorker extends Thread{
        private final Process process;
        private volatile int exitCode=-99;
        private volatile boolean completed=false;
        private volatile String output="";
        private ProcessWorker(Process process){
            this.process=process;
        }

        @Override
        public void run(){
            InputStream inputStreamInput = null;
            InputStream inputStreamError = null;
            try{



                inputStreamInput = process.getInputStream();
                inputStreamError = process.getErrorStream();

                BufferedReader readerInput = null;

                //注意：windows和linux编码不一样
                if (ConfigConstant.windows)
                    readerInput = new BufferedReader(new InputStreamReader(inputStreamInput, Charset.forName("GB18030")));
                else
                    readerInput = new BufferedReader(new InputStreamReader(inputStreamInput, StandardCharsets.UTF_8));





                StringBuilder resultInput=new StringBuilder();
                char[] buffer=new char[BUFFER_SIZE];
                int length=0;
                while((length=readerInput.read(buffer))!=-1){
                    resultInput.append(buffer,0,length);
                }

                //注意：windows和linux编码不一样
                BufferedReader readerError = null;
                if (ConfigConstant.windows) {
                    readerError = new BufferedReader(new InputStreamReader(inputStreamError, Charset.forName("GB18030")));
                } else
                    readerError = new BufferedReader(new InputStreamReader(inputStreamError, StandardCharsets.UTF_8));
                StringBuilder resultError=new StringBuilder();
                while((length=readerError.read(buffer))!=-1){
                    resultError.append(buffer,0,length);
                }


                output=resultInput.toString();
                exitCode=process.waitFor();
                completed=true;

                if (inputStreamInput != null)
                    inputStreamInput.close();
                if (inputStreamError != null)
                    inputStreamError.close();
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
            }finally {

            }

        }

        public int getExitCode(){
            return exitCode;
        }

        public String getOutput(){
            return output;
        }

        public boolean isCompleted(){
            return completed;
        }
    }

    public static String runPython(String pythonProgram,
                                String input,
                                String envDir,
                                String tempDir) throws IOException, TimeoutException{

        if (pythonProgram == null || pythonProgram.length() == 0)
            return null;

        File tempDirFile = new File(tempDir);
        if (!tempDirFile.exists() && !tempDirFile.isDirectory()) {
            tempDirFile.mkdir();
        }


        File file = File.createTempFile("pytemp", ".py", new File(tempDir));


        if (file.canWrite()) {

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            out.write(pythonProgram);
            out.close();
        }


        String[] inputStrings = input.split("\n");


        String[] cmd = new String[2];
        cmd[0] = ConfigConstant.PythonCmd;
        cmd[1] = file.getAbsolutePath();

        ProcessBuilder processBuilder = new ProcessBuilder(cmd).directory(new File(envDir));
        Map<String, String> env = processBuilder.environment();
        env.clear();
        env.put("LANG", "zh_CN.GB18030");
        env.put("PATH", envDir);


        //Process process = Runtime.getRuntime().exec(cmd);
        Process process = processBuilder.start();

        OutputStream outputStream = process.getOutputStream();
        // BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("GB18030")));


        for (int i = 0; i < inputStrings.length; i++) {
            if (!inputStrings[i].isEmpty()) {
                outputStream.write(inputStrings[i].getBytes("GB18030"));
                outputStream.write("\n".getBytes());
            }
        }

        outputStream.flush();
        outputStream.close();

      //  System.out.println("开始线程");

        ProcessWorker processWorker=new ProcessWorker(process);
        int exitCode=processWorker.getExitCode();
        processWorker.start();

        StringBuilder result=new StringBuilder();
        try {
            //主线程等待子线程终止
            processWorker.join(ConfigConstant.Python_Timeout_Second* 1000L);
            if(processWorker.isCompleted()){
                result.append(processWorker.getOutput());
                exitCode=processWorker.getExitCode();
             //   System.out.println("线程正常结束");
            }else{
             //   System.out.println("线程超时");
                process.destroyForcibly();
                processWorker.interrupt();
                throw new TimeoutException("Process执行时间超时");
            }

        } catch (InterruptedException e) {
           // System.out.println("线程超时 exception");
            process.destroyForcibly();
            processWorker.interrupt();
        }finally {
           // System.out.println("线程超时 finally");
            process.destroyForcibly();//强制销毁进程
            processWorker.interrupt();
        }
        return result.toString();
    }
}
