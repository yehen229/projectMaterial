package cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RunPythonUtils {

    /**
     * 比较两个字符串，如果相等，返回true，否则返回false
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean compare(String a,
                                  String b) {
        return format(a).equals(format(b));
    }

    public static String format(String str) {
        //1,去掉空格
        str = str.replace(" ", "");


        //2.去掉\n,\t,\r等字符
        str = str.replace("\n", "");


        str = str.replace("\r", "");


        str = str.replace("\t", "");


        //3.去掉。，{}【】（）等中文标点符号
        str = str.replace("。", ".");


        str = str.replace("，", ",");


        str = str.replace("（", "(");


        str = str.replace("）", ")");


        str = str.replace("【", "[");


        str = str.replace("】", "]");


        str = str.replace("：", ":");


        str = str.replace("？", "?");


        str = str.replace("《", "<");


        str = str.replace("》", ">");


        str = str.replace("”", "\"");


        str = str.replace("“", "\"");


        str = str.replace("‘", "'");

        return str;

    }

    public String printResults(Process process)
            throws IOException, InterruptedException {

        InputStream inputStreamInput = null;
        InputStream inputStreamError = null;


        try {

            inputStreamInput = process.getInputStream();
            inputStreamError = process.getErrorStream();

            BufferedReader readerInput = null;

            //注意：windows和linux编码不一样
            if (ConfigConstant.windows)
                readerInput = new BufferedReader(new InputStreamReader(inputStreamInput, Charset.forName("GB18030")));
            else
                readerInput = new BufferedReader(new InputStreamReader(inputStreamInput, StandardCharsets.UTF_8));

            String lineInput = "";

            String resultInput = "";



            /**
             * readLine为阻塞函数，当没有数据读取时，就一直会阻塞在那，而不是返回null。这样会导致CPU利用率100%
             * readLine()只有在数据流发生异常或者另一端被close()掉时，才会返回null值。
             */
            while ((lineInput = readerInput.readLine()) != null) {
                resultInput += (lineInput);
            }

            //注意：windows和linux编码不一样
            BufferedReader readerError = null;
            if (ConfigConstant.windows) {
                readerError = new BufferedReader(new InputStreamReader(inputStreamError, Charset.forName("GB18030")));
            } else
                readerError = new BufferedReader(new InputStreamReader(inputStreamError, StandardCharsets.UTF_8));


            String lineError = "";
            String resultError = "";


            while ((lineError = readerError.readLine()) != null) {
                resultError += (lineError);
            }


            //System.out.println(resultError);
            boolean exitVal = process.waitFor(5, TimeUnit.MINUTES);//5分钟，防止锁死

            if (exitVal) {
                //System.out.println("成功");
                return resultInput;
            } else {
                //System.out.println("失败");
                throw new BusinessException(resultError);
            }
        } catch (IOException exception) {
            throw new BusinessException("error");
        } catch (NullPointerException exception) {
            throw new BusinessException("error");
        } catch (Exception exception) {
            throw new BusinessException("error");
        } finally {
            if (inputStreamInput != null)
                inputStreamInput.close();
            if (inputStreamError != null)
                inputStreamError.close();
            process.destroy();
        }


/*
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
        String line = "";
        String result = "";

        while ((line = reader.readLine()) != null) {
            result += (line);
        }



        boolean exitVal = process.waitFor(5, TimeUnit.MINUTES);//5分钟，防止锁死
        System.out.println(exitVal ? "成功" : "失败");

        return result;*/
    }

    public String run(String pythonProgram,
                      String input)
            throws IOException, InterruptedException,TimeoutException {
        return run(pythonProgram, input, ConfigConstant.FilePath, ConfigConstant.FilePath);

    }

    public String run(String pythonProgram,
                      String input,
                      String envDir)
            throws IOException, InterruptedException,TimeoutException {
        if (envDir != null)
            return run(pythonProgram, input, envDir, ConfigConstant.FilePath);

        return run(pythonProgram, input);
    }

    /**
     * @param pythonProgram
     * @param input
     * @param envDir        环境变量目录
     * @param tempDir       临时文件目录
     * @return
     * @throws IOException
     * @throws InterruptedException
     */

    public String run(String pythonProgram,
                      String input,
                      String envDir,
                      String tempDir
    )
            throws IOException, InterruptedException,TimeoutException {

            return ProcessUtil.runPython(pythonProgram,input,envDir,tempDir);


/*



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


        String result = printResults(process);
        //System.out.println(result);

        process.destroyForcibly();//强制销毁进程

        file.delete();
        return result;*/
    }
}
