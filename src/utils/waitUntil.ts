const waitUntil = (
  condition: () => Promise<boolean>,
  timeout?: number,
  interval?: number,
) => {
  if (timeout === void 0) {
    timeout = 0
  } // 如果不设置超时参数，表示永不超时，一直等待
  if (interval === void 0) {
    interval = 1000
  } // 默认查询间隔1000ms
  let waitHandler
  let timeoutHandler
  return new Promise<void>(function (resolve, reject) {
    const waitFn = async () => {
      const r = await condition()
      if (r) {
        if (timeoutHandler) {
          clearTimeout(timeoutHandler)
        }
        resolve()
      } else {
        waitHandler = setTimeout(waitFn, interval)
      }
    }
    // 定时检查
    waitHandler = setTimeout(waitFn, interval)

    // 超时判定
    if (timeout > 0) {
      timeoutHandler = setTimeout(() => {
        if (waitHandler) {
          clearTimeout(waitHandler)
        }

        reject({
          code: 'TIMEOUT',
          message: 'timeout',
        })
      }, timeout)
    }
  })
}
export { waitUntil }
