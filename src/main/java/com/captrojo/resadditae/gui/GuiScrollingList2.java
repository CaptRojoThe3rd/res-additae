package com.captrojo.resadditae.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

public abstract class GuiScrollingList2
{
	protected final Minecraft mc;
	protected final int list_width;
	protected final int list_height;
	protected final int top_y;
	protected final int bottom_y;
	protected final int left_x;
	protected final int right_x;
	protected final int element_height;
	
	protected int scrollbar_color_bg = 0x000000;
	protected int scrollbar_color_shadow = 0x808080;
	protected int scrollbar_color_fg = 0xc0c0c0;
	
	protected int scroll_up_action_id;
	protected int scroll_down_action_id;
	protected int mouse_x;
	protected int mouse_y;
	protected float initial_mouse_click_y = -2.0f;
	protected float scroll_factor;
	protected float scroll_distance;
	protected int selected_index = -1;
	protected long last_click_time = 0L;
	
	protected boolean highlight_selected_element = true;
	protected boolean field_27262_q;
	protected int field_27261_r;
	protected boolean draw_list_background = true;
	protected boolean draw_gradient_rect = true;
	protected boolean draw_edge_gradient = true;

	public GuiScrollingList2(Minecraft mc, int w, int h, int top, int bottom, int left, int element_height)
	{
		this.mc = mc;
		this.list_width = w;
		this.list_height = h;
		this.top_y = top;
		this.bottom_y = bottom;
		this.element_height = element_height;
		this.left_x = left;
		this.right_x = w + this.left_x;
	}

	protected abstract int getSize();

	protected abstract void elementClicked(int idx, boolean double_click);

	protected abstract boolean isSelected(int idx);

	protected abstract void drawBackground();

	protected abstract void drawSlot(int idx, int element_right_x, int element_y, int element_h, Tessellator ts);

	protected void func_27260_a(int p_27260_1_, int p_27260_2_, Tessellator ts)
	{
	}

	protected void func_27255_a(int p_27255_1_, int p_27255_2_)
	{
	}

	protected void func_27257_b(int p_27257_1_, int p_27257_2_)
	{
	}

	public void setHighlightSelected(boolean p_27258_1_)
	{
		this.highlight_selected_element = p_27258_1_;
	}

	protected void func_27259_a(boolean p_27259_1_, int p_27259_2_)
	{
		this.field_27262_q = p_27259_1_;
		this.field_27261_r = p_27259_2_;

		if (!p_27259_1_) {
			this.field_27261_r = 0;
		}
	}

	public int func_27256_c(int p_27256_1_, int p_27256_2_)
	{
		int var3 = this.left_x + 1;
		int var4 = this.left_x + this.list_width - 7;
		int var5 = p_27256_2_ - this.top_y - this.field_27261_r + (int) this.scroll_distance - 4;
		int var6 = var5 / this.element_height;
		return p_27256_1_ >= var3 && p_27256_1_ <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
	}
	
	public boolean checkMousePos(int left, int right, int top, int bottom)
	{
		if (this.mouse_x < left || this.mouse_x > right) {
			return false;
		}
		if (this.mouse_y < top || this.mouse_y > bottom) {
			return false;
		}
		return true;
	}
	
	protected int getContentHeight()
	{
		return this.getSize() * this.element_height + this.field_27261_r;
	}

	public void registerScrollButtons(List p_22240_1_, int scroll_up_id, int scroll_down_id)
	{
		this.scroll_up_action_id = scroll_up_id;
		this.scroll_down_action_id = scroll_down_id;
	}

	protected void applyScrollLimits()
	{
		int var1 = this.getContentHeight() - (this.bottom_y - this.top_y - 4);

		if (var1 < 0) {
			var1 /= 2;
		}

		if (this.scroll_distance < 0.0f) {
			this.scroll_distance = 0.0f;
		}

		if (this.scroll_distance > (float) var1) {
			this.scroll_distance = (float) var1;
		}
	}

	public void actionPerformed(GuiButton button)
	{
		if (button.enabled) {
			if (button.id == this.scroll_up_action_id) {
				this.scroll_distance -= (float) (this.element_height * 2 / 3);
				this.initial_mouse_click_y = -2.0f;
				this.applyScrollLimits();
			} else if (button.id == this.scroll_down_action_id) {
				this.scroll_distance += (float) (this.element_height * 2 / 3);
				this.initial_mouse_click_y = -2.0f;
				this.applyScrollLimits();
			}
		}
	}

	public void drawScreen(int mouse_x, int mouse_y, float partial_ticks)
	{
		this.mouse_x = mouse_x;
		this.mouse_y = mouse_y;
		this.drawBackground();
		int list_length = this.getSize();
		int scroll_bar_x_start = this.left_x + this.list_width - 6;
		int scroll_bar_x_end = scroll_bar_x_start + 6;
		int element_left_x = this.left_x;
		int element_right_x = scroll_bar_x_start - 1;
		int adj_mouse_y;
		int idx;
		int height1;
		int element_y;

		if (Mouse.isButtonDown(0)) {
			if (this.initial_mouse_click_y == -1.0f) {
				boolean var7 = true;

				if (mouse_y >= this.top_y && mouse_y <= this.bottom_y) {
					adj_mouse_y = mouse_y - this.top_y - this.field_27261_r + (int) this.scroll_distance - 4;
					idx = adj_mouse_y / this.element_height;

					if (mouse_x >= element_left_x && mouse_x <= element_right_x && idx >= 0 && adj_mouse_y >= 0 && idx < list_length) {
						boolean var12 = idx == this.selected_index && System.currentTimeMillis() - this.last_click_time < 250L;
						this.elementClicked(idx, var12);
						this.selected_index = idx;
						this.last_click_time = System.currentTimeMillis();
					} else if (mouse_x >= element_left_x && mouse_x <= element_right_x && adj_mouse_y < 0) {
						this.func_27255_a(mouse_x - element_left_x, mouse_y - this.top_y + (int) this.scroll_distance - 4);
						var7 = false;
					}

					if (mouse_x >= scroll_bar_x_start && mouse_x <= scroll_bar_x_end) {
						this.scroll_factor = -1.0f;
						element_y = this.getContentHeight() - (this.bottom_y - this.top_y - 4);

						if (element_y < 1) {
							element_y = 1;
						}

						height1 = (int) ((float) ((this.bottom_y - this.top_y) * (this.bottom_y - this.top_y)) / (float) this.getContentHeight());

						if (height1 < 32) {
							height1 = 32;
						}

						if (height1 > this.bottom_y - this.top_y - 8) {
							height1 = this.bottom_y - this.top_y - 8;
						}

						this.scroll_factor /= (float) (this.bottom_y - this.top_y - height1) / (float) element_y;
					} else {
						this.scroll_factor = 1.0f;
					}

					if (var7) {
						this.initial_mouse_click_y = (float) mouse_y;
					} else {
						this.initial_mouse_click_y = -2.0f;
					}
				} else {
					this.initial_mouse_click_y = -2.0f;
				}
			} else if (this.initial_mouse_click_y >= 0.0f) {
				this.scroll_distance -= ((float) mouse_y - this.initial_mouse_click_y) * this.scroll_factor;
				this.initial_mouse_click_y = (float) mouse_y;
			}
		} else {
			if (mouse_x > this.left_x && mouse_x < this.right_x && mouse_y > this.top_y && mouse_y < this.bottom_y) {
				while (Mouse.next()) {
					int var16 = Mouse.getEventDWheel();

					if (var16 != 0) {
						if (var16 > 0) {
							var16 = -1;
						} else if (var16 < 0) {
							var16 = 1;
						}

						this.scroll_distance += (float) (var16 * this.element_height / 2);
					}
				}

				this.initial_mouse_click_y = -1.0f;
			}
		}

		this.applyScrollLimits();
		
		Tessellator ts = Tessellator.instance;
		if (this.mc.theWorld != null) {
			this.drawGradientRect(this.left_x, this.top_y, this.right_x, this.bottom_y, -1072689136, -804253680);
		} else {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_FOG);
			this.drawListBackground();
		}
		adj_mouse_y = this.top_y + 4 - (int) this.scroll_distance;

		if (this.field_27262_q) {
			this.func_27260_a(element_right_x, adj_mouse_y, ts);
		}

		int scroll_bar_y;

		for (idx = 0; idx < list_length; ++idx) {
			element_y = adj_mouse_y + idx * this.element_height + this.field_27261_r;
			height1 = this.element_height - 4;

			if (element_y <= this.bottom_y && element_y + height1 >= this.top_y) {
				if (this.highlight_selected_element && this.isSelected(idx)) {
					this.drawSelectedElementHighlight(element_left_x, element_right_x, element_y, height1);
				}

				this.drawSlot(idx, element_right_x, element_y, height1, ts);
			}
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		if (this.mc.theWorld == null) {
			this.drawOverlayBackground(0, this.top_y, 255, 255);
			this.drawOverlayBackground(this.bottom_y, this.list_height, 255, 255);
		}
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		this.drawEdgeGradient();
		
		element_y = this.getContentHeight() - (this.bottom_y - this.top_y - 4);

		if (element_y > 0) {
			height1 = (this.bottom_y - this.top_y) * (this.bottom_y - this.top_y) / this.getContentHeight();

			if (height1 < 32) {
				height1 = 32;
			}

			if (height1 > this.bottom_y - this.top_y - 8) {
				height1 = this.bottom_y - this.top_y - 8;
			}

			scroll_bar_y = (int) this.scroll_distance * (this.bottom_y - this.top_y - height1) / element_y + this.top_y;

			if (scroll_bar_y < this.top_y) {
				scroll_bar_y = this.top_y;
			}

			ts.startDrawingQuads();
			ts.setColorRGBA_I(this.scrollbar_color_bg, 255);
			ts.addVertexWithUV((double) scroll_bar_x_start, (double) this.bottom_y, 0.0, 0.0, 1.0);
			ts.addVertexWithUV((double) scroll_bar_x_end, (double) this.bottom_y, 0.0, 1.0, 1.0);
			ts.addVertexWithUV((double) scroll_bar_x_end, (double) this.top_y, 0.0, 1.0, 0.0);
			ts.addVertexWithUV((double) scroll_bar_x_start, (double) this.top_y, 0.0, 0.0, 0.0);
			ts.draw();
			ts.startDrawingQuads();
			ts.setColorRGBA_I(this.scrollbar_color_shadow, 255);
			ts.addVertexWithUV((double) scroll_bar_x_start, (double) (scroll_bar_y + height1), 0.0, 0.0, 1.0);
			ts.addVertexWithUV((double) scroll_bar_x_end, (double) (scroll_bar_y + height1), 0.0, 1.0, 1.0);
			ts.addVertexWithUV((double) scroll_bar_x_end, (double) scroll_bar_y, 0.0, 1.0, 0.0);
			ts.addVertexWithUV((double) scroll_bar_x_start, (double) scroll_bar_y, 0.0, 0.0, 0.0);
			ts.draw();
			ts.startDrawingQuads();
			ts.setColorRGBA_I(this.scrollbar_color_fg, 255);
			ts.addVertexWithUV((double) scroll_bar_x_start, (double) (scroll_bar_y + height1 - 1), 0.0, 0.0, 1.0);
			ts.addVertexWithUV((double) (scroll_bar_x_end - 1), (double) (scroll_bar_y + height1 - 1), 0.0, 1.0, 1.0);
			ts.addVertexWithUV((double) (scroll_bar_x_end - 1), (double) scroll_bar_y, 0.0, 1.0, 0.0);
			ts.addVertexWithUV((double) scroll_bar_x_start, (double) scroll_bar_y, 0.0, 0.0, 0.0);
			ts.draw();
		}

		this.func_27257_b(mouse_x, mouse_y);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	protected void drawListBackground()
	{
		if (!this.draw_list_background) {
			return;
		}
		Tessellator ts = Tessellator.instance;
		this.mc.renderEngine.bindTexture(Gui.optionsBackground);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		float var17 = 32.0f;
		ts.startDrawingQuads();
		ts.setColorOpaque_I(0x202020);
		ts.addVertexWithUV((double) this.left_x, (double) this.bottom_y, 0.0, (double) ((float) this.left_x / var17), (double) ((float) (this.bottom_y + (int) this.scroll_distance) / var17));
		ts.addVertexWithUV((double) this.right_x, (double) this.bottom_y, 0.0, (double) ((float) this.right_x / var17), (double) ((float) (this.bottom_y + (int) this.scroll_distance) / var17));
		ts.addVertexWithUV((double) this.right_x, (double) this.top_y, 0.0, (double) ((float) this.right_x / var17), (double) ((float) (this.top_y + (int) this.scroll_distance) / var17));
		ts.addVertexWithUV((double) this.left_x, (double) this.top_y, 0.0, (double) ((float) this.left_x / var17), (double) ((float) (this.top_y + (int) this.scroll_distance) / var17));
		ts.draw();
	}

	protected void drawOverlayBackground(int y1, int y2, int alpha_1, int alpha_2)
	{
		Tessellator ts = Tessellator.instance;
		this.mc.renderEngine.bindTexture(Gui.optionsBackground);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		float var6 = 32.0f;
		ts.startDrawingQuads();
		ts.setColorRGBA_I(0x404040, alpha_2);
		ts.addVertexWithUV(0.0, (double) y2, 0.0, 0.0, (double) ((float) y2 / var6));
		ts.addVertexWithUV((double) this.list_width + 30, (double) y2, 0.0, (double) ((float) (this.list_width + 30) / var6), (double) ((float) y2 / var6));
		ts.setColorRGBA_I(0x404040, alpha_1);
		ts.addVertexWithUV((double) this.list_width + 30, (double) y1, 0.0, (double) ((float) (this.list_width + 30) / var6), (double) ((float) y1 / var6));
		ts.addVertexWithUV(0.0, (double) y1, 0.0, 0.0, (double) ((float) y1 / var6));
		ts.draw();
	}

	protected void drawGradientRect(int x1, int y1, int x2, int y2, int color_1, int color_2)
	{
		if (!this.draw_gradient_rect) {
			return;
		}
		float a1 = (float) (color_1 >> 24 & 255) / 255.0f;
		float r1 = (float) (color_1 >> 16 & 255) / 255.0f;
		float g1 = (float) (color_1 >> 8 & 255) / 255.0f;
		float b1 = (float) (color_1 & 255) / 255.0f;
		float a2 = (float) (color_2 >> 24 & 255) / 255.0f;
		float r2 = (float) (color_2 >> 16 & 255) / 255.0f;
		float g2 = (float) (color_2 >> 8 & 255) / 255.0f;
		float b2 = (float) (color_2 & 255) / 255.0f;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator ts = Tessellator.instance;
		ts.startDrawingQuads();
		ts.setColorRGBA_F(r1, g1, b1, a1);
		ts.addVertex((double) x2, (double) y1, 0.0);
		ts.addVertex((double) x1, (double) y1, 0.0);
		ts.setColorRGBA_F(r2, g2, b2, a2);
		ts.addVertex((double) x1, (double) y2, 0.0);
		ts.addVertex((double) x2, (double) y2, 0.0);
		ts.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	protected void drawEdgeGradient()
	{
		if (!this.draw_edge_gradient) {
			return;
		}
		
		Tessellator ts = Tessellator.instance;
		byte edge_gradient_size = 4;
		
		ts.startDrawingQuads();
		ts.setColorRGBA_I(0x000000, 0);
		ts.addVertexWithUV((double) this.left_x, (double) (this.top_y + edge_gradient_size), 0.0, 0.0, 1.0);
		ts.addVertexWithUV((double) this.right_x, (double) (this.top_y + edge_gradient_size), 0.0, 1.0, 1.0);
		ts.setColorRGBA_I(0x000000, 255);
		ts.addVertexWithUV((double) this.right_x, (double) this.top_y, 0.0, 1.0, 0.0);
		ts.addVertexWithUV((double) this.left_x, (double) this.top_y, 0.0, 0.0, 0.0);
		ts.draw();
		ts.startDrawingQuads();
		ts.setColorRGBA_I(0x000000, 255);
		ts.addVertexWithUV((double) this.left_x, (double) this.bottom_y, 0.0, 0.0, 1.0);
		ts.addVertexWithUV((double) this.right_x, (double) this.bottom_y, 0.0, 1.0, 1.0);
		ts.setColorRGBA_I(0x000000, 0);
		ts.addVertexWithUV((double) this.right_x, (double) (this.bottom_y - edge_gradient_size), 0.0, 1.0, 0.0);
		ts.addVertexWithUV((double) this.left_x, (double) (this.bottom_y - edge_gradient_size), 0.0, 0.0, 0.0);
		ts.draw();
	}
	
	protected void drawSelectedElementHighlight(int left_x, int right_x, int y, int height)
	{
		int ylt = this.top_y + 4;
		int ylb = this.bottom_y - 4;
		if (y < ylt) {
			height -= (ylt - y);
			y = ylt;
		} else if ((y + height) > ylb) {
			height -= (y + height) - ylb;
		}
		
		Tessellator ts = Tessellator.instance;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		ts.startDrawingQuads();
		ts.setColorOpaque_I(0x808080);
		ts.addVertexWithUV((double) left_x, (double) (y + height + 2), 0.0, 0.0, 1.0);
		ts.addVertexWithUV((double) right_x, (double) (y + height + 2), 0.0, 1.0, 1.0);
		ts.addVertexWithUV((double) right_x, (double) (y - 2), 0.0, 1.0, 0.0);
		ts.addVertexWithUV((double) left_x, (double) (y - 2), 0.0, 0.0, 0.0);
		ts.setColorOpaque_I(0x000000);
		ts.addVertexWithUV((double) (left_x + 1), (double) (y + height + 1), 0.0, 0.0, 1.0);
		ts.addVertexWithUV((double) (right_x - 1), (double) (y + height + 1), 0.0, 1.0, 1.0);
		ts.addVertexWithUV((double) (right_x - 1), (double) (y - 1), 0.0, 1.0, 0.0);
		ts.addVertexWithUV((double) (left_x + 1), (double) (y - 1), 0.0, 0.0, 0.0);
		ts.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
