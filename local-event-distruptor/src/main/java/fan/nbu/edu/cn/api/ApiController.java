package fan.nbu.edu.cn.api;

import fan.nbu.edu.cn.producer.BaseProducer;
import fan.nbu.edu.cn.prometheus.metrix.MyMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-15:15
 * @since 1.0
 */
@RestController
@RequestMapping("/api/")
@Slf4j
public class ApiController {

    @Resource
    private MyMeterRegistry meterRegistry;
    @Resource
    private BaseProducer  baseProducer;

    @GetMapping("metrics/test")
    public void test(HttpServletResponse response) {
        try {
            baseProducer.add("test");
            Writer writer = response.getWriter();
            Throwable throwable = null;

            try {
                response.setStatus(200);
                response.setContentType("text/plain; version=0.0.4; charset=utf-8");
                if (null != this.meterRegistry) {
                    this.meterRegistry.scrape(writer);
                } else {
                    writer.append("# HELP no data");
                }
                writer.flush();
            } catch (Throwable throwable1) {
                throwable = throwable1;
                throw throwable1;
            } finally {
                if (writer != null) {
                    if (throwable != null) {
                        try {
                            writer.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    } else {
                        writer.close();
                    }
                }

            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }
}

