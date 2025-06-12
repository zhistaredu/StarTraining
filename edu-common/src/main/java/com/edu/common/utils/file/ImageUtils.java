package com.edu.common.utils.file;

import java.awt.geom.AffineTransform;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import com.edu.common.config.EduConfig;
import com.edu.common.constant.Constants;
import com.edu.common.utils.seal.SealFont;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edu.common.utils.StringUtils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 图片处理工具类
 *
 * @author edu
 */
public class ImageUtils
{
    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static byte[] getImage(String imagePath)
    {
        InputStream is = getFile(imagePath);
        try
        {
            return IOUtils.toByteArray(is);
        }
        catch (Exception e)
        {
            log.error("图片加载异常 {}", e);
            return null;
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    public static InputStream getFile(String imagePath)
    {
        try
        {
            byte[] result = readFile(imagePath);
            result = Arrays.copyOf(result, result.length);
            return new ByteArrayInputStream(result);
        }
        catch (Exception e)
        {
            log.error("获取图片异常 {}", e);
        }
        return null;
    }

    /**
     * 读取文件为字节数据
     *
     * @param url 地址
     * @return 字节数据
     */
    public static byte[] readFile(String url)
    {
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try
        {
            if (url.startsWith("http"))
            {
                // 网络地址
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setReadTimeout(60 * 1000);
                urlConnection.setDoInput(true);
                in = urlConnection.getInputStream();
            }
            else
            {
                // 本机地址
                String localPath = EduConfig.getProfile();
                String downloadPath = localPath + StringUtils.substringAfter(url, Constants.RESOURCE_PREFIX);
                in = new FileInputStream(downloadPath);
            }
            return IOUtils.toByteArray(in);
        }
        catch (Exception e)
        {
            log.error("获取文件路径异常 {}", e);
            return null;
        }
        finally
        {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(baos);
        }
    }

    /**
     * 读取本地图片
     *
     * @param path 本地图片存放路径
     */
    public static Image readLocalPicture(String path) {
        if (null == path) {
            throw new RuntimeException("本地图片路径不能为空");
        }
        // 读取原图片信息 得到文件
        File srcImgFile = new File(path);
        try {
            // 将文件对象转化为图片对象
            return ImageIO.read(srcImgFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取网络图片
     *
     * @param path 网络图片地址
     */
    public static Image readNetworkPicture(String path) {
        if (null == path) {
            throw new RuntimeException("网络图片路径不能为空");
        }
        try {
            // 创建一个URL对象,获取网络图片的地址信息
            URL url = new URL(path);
            // 将URL对象输入流转化为图片对象 (url.openStream()方法，获得一个输入流)
            BufferedImage bugImg = ImageIO.read(url.openStream());
            if (null == bugImg) {
                throw new RuntimeException("网络图片地址不正确");
            }
            return bugImg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] makeColumCertificate(String coverImg,String name,String content,String certNumber,String createTime,String expireTime,String companyName) throws IOException {

        // 读取印章图片
        URL sealUrl = new URL(coverImg);
        BufferedImage stampImage = ImageIO.read(sealUrl.openStream());

        int width = 2480;
        int height = 3507;

        // 创建新的BufferedImage对象作为输出结果
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();

        // 设置抗锯齿渲染和平滑处理
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 绘制原始图片
        g2d.drawImage(stampImage, 0, 0, width, height, null);

        // 设置字体大小
        g2d.setPaint(Color.decode("#000000"));
        g2d.setFont(new Font("SimSun", Font.BOLD, 100));

        // 获取水印文字的宽度和高度
        int nameWidth = g2d.getFontMetrics().stringWidth(name);

        // 计算水印文字的位置
        int x = (width - nameWidth) / 2;
        //设置证书名称
        g2d.drawString(name, x, 600);
        //设置内容
        drawContent(g2d,outputImage,content);
        //设置颁发人信息
        List<String> params = new ArrayList<>();
        params.add("证书编号：" + certNumber);
        params.add("颁发日期：" + createTime);
        params.add("到期日期：" + expireTime);
        drawColumOrderData(g2d,params);
        //设置盖章位置文字
        g2d.drawString("签名/盖章：", 1650, 3000);
        //下面是绘制印章
        g2d.setPaint(Color.decode("#bd6567"));
        SealFont mainFont = new SealFont();
        mainFont.setSize(42);
        mainFont.setText(companyName);
        mainFont.setSpace(40.0);
        mainFont.setMargin(18);
        //6.画弧形主文字
        drawColumArcFont4Circle(g2d, 175, mainFont);
        String code = "https://sdn.coolcollege.cn/assets/kuxueyuan/certificate/seal-zh.png";
        Image srcCodeImage = readNetworkPicture(code);
        // 设置 alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
        // 绘制水印图片  坐标为中间位置
        g2d.drawImage(srcCodeImage,1820, 2850, 450,450,null);
        g2d.dispose();
        // 保存图片
        //生成字节数组，给上传oss做准备
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    public static byte[] makeRowCertificate(String coverImg,String name,String content,String certNumber,String createTime,String expireTime,String companyName) throws IOException {

        // 读取印章图片
        URL sealUrl = new URL(coverImg);
        BufferedImage stampImage = ImageIO.read(sealUrl.openStream());

        int width = 3507;
        int height = 2480;

        // 创建新的BufferedImage对象作为输出结果
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();

        // 设置抗锯齿渲染和平滑处理
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 绘制原始图片
        g2d.drawImage(stampImage, 0, 0, width, height, null);

        // 设置字体大小
        g2d.setPaint(Color.decode("#000000"));
        g2d.setFont(new Font("SimSun", Font.BOLD, 100));

        // 获取水印文字的宽度和高度
        int nameWidth = g2d.getFontMetrics().stringWidth(name);

        // 计算水印文字的位置
        int x = (width - nameWidth) / 2;
        //设置logo
        //设置证书名称
        g2d.drawString(name, x, 350);
        //设置内容
        drawContent(g2d,outputImage,content);
        //设置颁发人信息
        List<String> params = new ArrayList<>();
        params.add("证书编号：" + certNumber);
        params.add("颁发日期：" + createTime);
        params.add("到期日期：" + expireTime);
        drawRowOrderData(g2d,params);
        //设置盖章位置文字
        g2d.drawString("签名/盖章：", 2650, 2020);
        //下面是绘制印章
        g2d.setPaint(Color.decode("#bd6567"));
        SealFont mainFont = new SealFont();
        mainFont.setSize(42);
        mainFont.setText(companyName);
        mainFont.setSpace(40.0);
        mainFont.setMargin(4);
        //6.画弧形主文字
        drawRowArcFont4Circle(g2d, 150, mainFont);
        String code = "https://sdn.coolcollege.cn/assets/kuxueyuan/certificate/seal-zh.png";
        Image srcCodeImage = readNetworkPicture(code);
        // 设置 alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
        // 绘制水印图片  坐标为中间位置
        g2d.drawImage(srcCodeImage,2750, 1750, 450,450,null);
        g2d.dispose();

        // 保存图片
        //生成字节数组，给上传oss做准备
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    private static void drawColumOrderData(Graphics2D g2d, List<String> params) {
        Font fonts = new Font("SimSun", Font.PLAIN, 36);
        g2d.setFont(fonts);
        int hg = 3100;
        for (String str : params){
            g2d.drawString(str, 250, hg);
            hg = hg - 100;
        }
    }

    private static void drawRowOrderData(Graphics2D g2d, List<String> params) {
        Font fonts = new Font("SimSun", Font.PLAIN, 36);
        g2d.setFont(fonts);
        int hg = 2000;
        for (String str : params){
            g2d.drawString(str, 350, hg);
            hg = hg - 100;
        }
    }

    private static void drawContent(Graphics2D g2d, BufferedImage outputImage, String content) {
        // 获取水印文字的宽度和高度
        Font fonts = new Font("SimSun", Font.PLAIN, 56);
        g2d.setFont(fonts);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textHeight = fontMetrics.getHeight();

        // 设置水印文字的最大宽度
        int maxWidth = 100;

        // 检查水印文字是否需要换行
        if (fontMetrics.stringWidth(content) <= maxWidth) {
            // 不需要换行，直接绘制水印文字
            int cx = (outputImage.getWidth() - fontMetrics.stringWidth(content)) / 2;
            int y = (outputImage.getHeight() - textHeight) / 2;
            g2d.drawString(content, cx, y);
        } else {
            // 需要换行，根据最大宽度进行分割和绘制
            int zishu = 1500 / 56;
            String[] lines = splitTextIntoLines(content, fontMetrics, maxWidth,zishu);
            int y = (outputImage.getHeight() - (textHeight * lines.length)) / 2;
            for (String line : lines) {
                int textWidth = fontMetrics.stringWidth(line);
                int cx = (outputImage.getWidth() - textWidth) / 2;
                g2d.drawString(line, cx, y);
                y += textHeight;
            }
        }
    }

    private static String[] splitTextIntoLines(String text, FontMetrics fontMetrics, int maxWidth,int zishu) {
        StringBuilder sb = new StringBuilder();
        String[] words = splitString(text,zishu);
        int lineCount = 0;
        for (String word : words) {
            if (fontMetrics.stringWidth(sb.toString() + " " + word) <= maxWidth) {
                sb.append(word).append(" ");
            } else {
                lineCount++;
                sb.setLength(0);
                sb.append(word).append(" ");
            }
        }
        lineCount++;
        String[] lines = new String[lineCount];
        sb.setLength(0);
        int lineIndex = 0;
        for (String word : words) {
            if (fontMetrics.stringWidth(sb.toString() + " " + word) <= maxWidth) {
                sb.append(word).append(" ");
            } else {
                lines[lineIndex++] = sb.toString().trim();
                sb.setLength(0);
                sb.append(word).append(" ");
            }
        }
        lines[lineIndex] = sb.toString().trim();
        return lines;
    }

    private static String[] splitString(String string, int length) {
        int splitCount = (int) Math.ceil((double) string.length() / length);
        String[] result = new String[splitCount];

        int startIndex = 0;
        for (int i = 0; i < splitCount; i++) {
            int endIndex = Math.min(startIndex + length, string.length());
            result[i] = string.substring(startIndex, endIndex);
            startIndex = endIndex;
        }

        return result;
    }

    /**
     * 绘制印章文字
     */
    private static void drawColumArcFont4Circle(Graphics2D g2d, int circleRadius, SealFont font) {

        //1.字体长度
        int textLen = font.getText().length();

        //2.字体大小，默认根据字体长度动态设定
        int size = font.getSize() == null ? (55 - textLen * 2) : font.getSize();

        //3.字体样式
        int style = font.getBold() ? Font.BOLD : Font.PLAIN;

        //4.构造字体
        Font f = new Font(font.getFamily(),  Font.PLAIN, size);

        //5.文字之间间距，默认动态调整
        double space = font.getSpace();

        //6.距离外圈距离
        int margin = font.getMargin() == null ? 5 : font.getMargin();

        //7.写字
        double newRadius = circleRadius - margin;
        double radianPerInterval = 2 * Math.asin(space / (2 * newRadius));

        double fix = 0.08;
        double firstAngle;
        if (textLen % 2 == 1) {
            firstAngle = (textLen - 1) * radianPerInterval / 2.0 + Math.PI / 2 + fix;
        } else {
            firstAngle = (textLen / 2.0 - 0.5) * radianPerInterval + Math.PI / 2 + fix;
        }

        for (int i = 0; i < textLen; i++) {
            double theta;
            double thetaX;
            double thetaY;

            theta = firstAngle - i * radianPerInterval;
            thetaX = newRadius * Math.sin(Math.PI / 2 - theta);
            thetaY = newRadius * Math.cos(theta - Math.PI / 2);

            AffineTransform transform = AffineTransform.getRotateInstance(Math.PI / 2 - theta + Math.toRadians(8));

            Font f2 = f.deriveFont(transform);
            g2d.setFont(f2);
            g2d.drawString(font.getText().substring(i, i + 1), (float) (circleRadius + thetaX + 1870), (float) (circleRadius - thetaY + 2900));
        }
    }

    /**
     * 绘制印章文字
     */
    private static void drawRowArcFont4Circle(Graphics2D g2d, int circleRadius, SealFont font) {

        //1.字体长度
        int textLen = font.getText().length();

        //2.字体大小，默认根据字体长度动态设定
        int size = font.getSize() == null ? (55 - textLen * 2) : font.getSize();

        //3.字体样式
        int style = font.getBold() ? Font.BOLD : Font.PLAIN;

        //4.构造字体
        Font f = new Font(font.getFamily(),  Font.PLAIN, size);

        //5.文字之间间距，默认动态调整
        double space = font.getSpace();

        //6.距离外圈距离
        int margin = font.getMargin() == null ? 5 : font.getMargin();

        //7.写字
        double newRadius = circleRadius - margin;
        double radianPerInterval = 2 * Math.asin(space / (2 * newRadius));

        double fix = 0.08;
        double firstAngle;
        if (textLen % 2 == 1) {
            firstAngle = (textLen - 1) * radianPerInterval / 2.0 + Math.PI / 2 + fix;
        } else {
            firstAngle = (textLen / 2.0 - 0.5) * radianPerInterval + Math.PI / 2 + fix;
        }

        for (int i = 0; i < textLen; i++) {
            double theta;
            double thetaX;
            double thetaY;

            theta = firstAngle - i * radianPerInterval;
            thetaX = newRadius * Math.sin(Math.PI / 2 - theta);
            thetaY = newRadius * Math.cos(theta - Math.PI / 2);

            AffineTransform transform = AffineTransform.getRotateInstance(Math.PI / 2 - theta + Math.toRadians(8));

            Font f2 = f.deriveFont(transform);
            g2d.setFont(f2);
            g2d.drawString(font.getText().substring(i, i + 1), (float) (circleRadius + thetaX + 2824), (float) (circleRadius - thetaY + 1810));
        }
    }
}
