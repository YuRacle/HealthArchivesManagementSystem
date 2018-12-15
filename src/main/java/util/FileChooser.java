package util;

import java.awt.*;

/**
 * Created by YuRacle on 2017/12/12.
 * 文件选择器
 * 调用系统的文件对话框
 */
public class FileChooser {
    //测试
    public static void main(String[] args) {
        new FileChooser();
    }

    private Frame Frame;//文件对话框依赖的窗口对象
    private FileDialog fileDialog;//文件对话框
    private String filePath;//文件路径
    private String fileName;//文件名

    public FileChooser() {

        fileDialog = new FileDialog(new Frame(),"选择电子书",FileDialog.LOAD);
        fileDialog.setVisible(true);

        filePath = fileDialog.getDirectory();
        fileName = fileDialog.getFile();
        if (filePath == null || fileName == null) {
            System.out.println("文件不存在！");
            fileDialog.dispose();
        } else {
            filePath=filePath+fileName;
            System.out.println(filePath);
            System.out.println(fileName);
            fileDialog.dispose();
        }

        fileDialog.dispose();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }
}
