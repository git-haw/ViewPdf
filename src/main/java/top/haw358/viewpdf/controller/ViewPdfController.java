package top.haw358.viewpdf.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by haw358linux@163.com on 2016-12-20.
 */
@Controller
@RequestMapping("/viewpdf/")
public class ViewPdfController {
    private static  final Log log = LogFactory.getLog(ViewPdfController.class);

    @RequestMapping("viewpdf")
    public String viewpdf(){
        return "WEB-INF/jsp/viewArbEvidence";
    }

    @RequestMapping("fetchFile")
    public void fetchFile(HttpServletResponse httpServletResponse) throws IOException {
        File pdf = ResourceUtils.getFile("classpath:a.pdf");
        FileInputStream fis = new FileInputStream(pdf);
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] buffer = new byte[bis.available()];
        bis.read(buffer);

        String fileName = pdf.getName();
        String fileType = "pdf";//fileName.substring(fileName.indexOf(".") + 1);

        httpServletResponse.reset();
        // 设置response的Header
        httpServletResponse.addHeader("Content-Disposition",
                "inline;filename=" + new String(fileName.getBytes("GB2312"), "iso8859-1"));
        httpServletResponse.addHeader("Content-Length", "" + buffer.length);

        OutputStream toClient = new BufferedOutputStream(httpServletResponse.getOutputStream());
        httpServletResponse.setContentType("application/" + fileType);
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }
}
