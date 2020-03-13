package cn.edu.cqu.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CodeUtil {
    private static int width = 90;//定义图片的width
    private static int height = 20;//定义图片的height
    private static int codeCount = 4;//定义图片上显示验证码的个数
    private static int xx = 15; //字符间隔距离
    private static int fontHeight = 18; //字体大小
    private static int codeY = 16; //字符高度
    private static char[] charSequence = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9'};

    /**
     * 生成一个Map集合
     * code为生成的验证码
     * codePic为生成的验证码BufferedImage对象
     */
    public static Map<String, Object> generateCodeAndPic() {
        //定义图像对象buffer
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gd = bufferedImage.getGraphics();
        //创建一个随机数生成器
        Random random = new Random();
        //将图片填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        //创建字体，字体大小根据图片高度确定
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        //设置字体
        gd.setFont(font);

        //画边框
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        //随机产生40条干扰线，使图片更难识别
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        //randomCode用于保存随机产生的验证码，以便用户登录后进行验证
        StringBuffer randomCode = new StringBuffer();
        int red = 0;
        int green = 0;
        int blue = 0;

        //随机生成验证码
        for (int i = 0; i < codeCount; i++) {
            //得到随机产生的验证码数字
            String code = String.valueOf(charSequence[random.nextInt(36)]);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            //用随机产生的颜色将验证码绘制到图像中
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, codeY);

            //将产生的验证码组合到一起
            randomCode.append(code);
        }
        Map<String, Object> map = new HashMap<>();
        //存放验证码
        map.put("code", randomCode);
        map.put("codePic", bufferedImage);
        return map;
    }

//    public static void main(String[] args) throws IOException {
//        OutputStream outputStream = new FileOutputStream("D:/randomCodePicture/" + System.currentTimeMillis() + ".jpg");
//        Map<String, Object> map = CodeUtil.generateCodeAndPic();
//        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", outputStream);
//        System.out.println("验证码的值为" + map.get("code"));
//    }
}
