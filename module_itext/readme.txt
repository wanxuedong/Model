itext模块

用于生成和查看pdf

操作步骤：

一：生成pdf文件

第一步，创建一个 iTextSharp.text.Document 对象的实例：
Document document = new Document();

第二步，为该 Document 创建一个 Writer 实例：
PdfWriter.getInstance(document, new FileStream("Chap0101.pdf",
FileMode.Create));

第三步，打开当前 Document
document.Open();

第四步，为当前 Document 添加内容：
document.Add(new Paragraph("Hello World"));

第五步，关闭 Document
document.Close();

二：打开pdf文件

try {
    FileUtils.openFile(mContext, new File(dest));
} catch (Exception e) {
    Log.d("TAG", "run: ERror");
}

额外介绍
（1）字体：
提供了四种字体，获取方式
PdfFont font = PdfFontFactory.createFont("assets/fonts/brandon_medium.otf", "UTF-8", true);

（2）分割线
LineSeparator lineSeparator = new LineSeparator(new DottedLine());
lineSeparator.setStrokeColor(new DeviceRgb(0, 0, 68));

