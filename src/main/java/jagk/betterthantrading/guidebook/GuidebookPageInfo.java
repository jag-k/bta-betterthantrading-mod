package jagk.betterthantrading.guidebook;

import net.minecraft.client.gui.guidebook.GuidebookPage;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;

import java.util.List;

public class GuidebookPageInfo extends GuidebookPage {

	private static final int PAGE_WIDTH = 110;
	private static final int LINE_HEIGHT = 9;

	private final String pageId;
	private final String title;
	private final String content;

	public GuidebookPageInfo(GuidebookSection section, String pageId, String title, String content) {
		super(section);
		this.pageId = pageId;
		this.title = title;
		this.content = content;
	}

	@Override
	protected void renderForeground(TextureManager textureManager, Font font, int x, int y, int mouseX, int mouseY, float partialTicks) {
		int textX = x + 17;
		int textY = y + 14;

		font.drawString(title, textX, textY, 0x8B4513);
		textY += 12;

		String[] paragraphs = content.split("\n");
		for (String paragraph : paragraphs) {
			if (paragraph.isEmpty()) {
				textY += 4;
			} else {
				List<String> wrappedLines = font.listFormattedStringToWidth(paragraph, PAGE_WIDTH);
				for (String line : wrappedLines) {
					font.drawString(line, textX, textY, 0x3F3F3F);
					textY += LINE_HEIGHT;
				}
			}
		}
	}

	public String getPageId() {
		return pageId;
	}
}
