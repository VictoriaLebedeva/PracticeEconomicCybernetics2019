import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Writer;

public class PrintGraphics extends Writer {
    private PrintJob job;
    private Graphics page;
    private Dimension pageSize;
    private int pagedpi;
    private int fontSize;
    private int originX;
    private int originY;
    private int width;
    private int height;
    private Font font, headerFont;
    private FontMetrics metrics;
    private FontMetrics headerMetrics;
    private int headery;
    private int charWidth;
    private int lineHeight;
    private int lineAscent;
    private int charsPerLine;
    private int linesPerPage;
    private int charNum = 0;
    private int lineNum = 0;
    private int pageNum = 0;
    private  String jobName;
    boolean isdraw = true;
    private boolean lastCharReturned = false;

    public PrintGraphics(String jobName, int fontSize, double leftMargin,
                         double rightMargin, double topMargin, double bottomMargin) {
        final Frame frame = new Frame();
        final Toolkit toolKit = frame.getToolkit();
        JobAttributes jobAttributes = new JobAttributes();
        jobAttributes.setSides(JobAttributes.SidesType.TWO_SIDED_LONG_EDGE);
        PageAttributes pageAttributes = new PageAttributes();
        pageAttributes.setOrientationRequested(PageAttributes.OrientationRequestedType.LANDSCAPE);
        job = toolKit.getPrintJob(frame, jobName, jobAttributes, pageAttributes);
        pageSize = job.getPageDimension();
        pagedpi = job.getPageResolution();
        if (System.getProperty("os.name").regionMatches(true, 0, "windows", 0,
                7)) {
            pagedpi = toolKit.getScreenResolution();
            pageSize = new Dimension((int) (8.5 * pagedpi), 11 * pagedpi);
            fontSize = (fontSize * pagedpi) / 72;
        }

        originX = (int) (leftMargin * pagedpi);
        originY = (int) (topMargin * pagedpi);
        width = pageSize.width - (int) ((leftMargin + rightMargin) * pagedpi);
        height = pageSize.height - (int) ((topMargin + bottomMargin) * pagedpi);

        font = new Font("Monospaced", Font.PLAIN, fontSize);
        metrics = frame.getFontMetrics(font);
        lineHeight = metrics.getHeight();
        lineAscent = metrics.getAscent();
        charWidth = metrics.charWidth('0');
        charsPerLine = (height / charWidth) - 15;
        linesPerPage = (width / lineHeight) - 6;

        headerFont = new Font("SansSerif", Font.ITALIC, fontSize);
        headerMetrics = frame.getFontMetrics(headerFont);
        headery = (originY - (int) (0.125 * pagedpi) - headerMetrics.getHeight())
                + headerMetrics.getAscent();
        this.jobName = jobName;
        this.fontSize = fontSize;
    }

    @Override
    public void write(char[] buffer, int index, int len) {
        for (int i = index; i < (index + len); i++) {
            if (page == null)
                newpage();
            if (isdraw) {
                final char[] chartName = "(x^2+y^2)^3-4a^2*x^2*y^2=0".toCharArray();
                final BufferedImage bufferedImage = new BufferedImage(400, 400,
                        BufferedImage.TYPE_INT_ARGB);
                final Graphics2D tg = bufferedImage.createGraphics();
                tg.setColor(Color.white);
                tg.fillRect(0, 0, 400, 400);
                tg.setColor(Color.BLUE);
                tg.setStroke(new StrokeLine(2f));
                tg.translate(155, 155);
                tg.draw(new ShapeLine(155));
                page.drawImage(bufferedImage, 50, (width / 2) - 200, null);
                page.drawChars(chartName, 0, chartName.length, 125, width - 175);
                isdraw = false;
            }
            if (buffer[i] == '\n') {
                if (!lastCharReturned)
                    newline();
                continue;
            }
            if (buffer[i] == '\r') {
                newline();
                lastCharReturned = true;
                continue;
            } else
                lastCharReturned = false;
            if (Character.isWhitespace(buffer[i])
                    && !Character.isSpaceChar(buffer[i]) && (buffer[i] != '\t'))
                continue;
            if (charNum >= charsPerLine) {
                newline();
                if (page == null)
                    newpage();
            }
            if (Character.isSpaceChar(buffer[i]))
                charNum++;
            else if (buffer[i] == '\t')
                charNum += 8 - (charNum % 8);
            else {
                if (page != null)
                    page.drawChars(buffer, i, 1, originX + (charNum * charWidth), originY
                            + (lineNum * lineHeight) + lineAscent);
                charNum++;
            }
        }
    }

        public void setFontStyle (int style) {
            final Font current = font;
            try {
                font = new Font("Monospaced", style, fontSize);
            } catch (final Exception e) {
                font = current;
            }
            if (page != null)
                page.setFont(font);
        }


        public void pageBreak () {
            newpage();
        }

        public int getCharactersPerLine () {
            return this.charsPerLine;
        }

        public int getLinesPerPage () {
            return this.linesPerPage;
        }

        protected void newline () {
            if (pageNum == 1)
                charNum = 350 / charWidth;
            else
                charNum = 0;
            lineNum++;
            if (lineNum >= linesPerPage) {
                page.dispose();
                page = null;
            }
        }

        protected void newpage () {
            page = job.getGraphics();
            lineNum = 0;
            charNum = 0;
            pageNum++;
            if (page != null)
                page.setFont(font);
    }

    @Override
    public void flush() throws IOException {

    }
    @Override
    public void close() throws IOException {
        if (page != null)
            page.dispose();
        job.end();
    }
}
