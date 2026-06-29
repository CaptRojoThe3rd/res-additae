package com.captrojo.resadditae.handler.nei;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.crafting.StonecutterRecipes;
import com.captrojo.resadditae.main.ResAdditae;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class StonecutterRecipeHandler extends TemplateRecipeHandler
{
	@Override
	public String getRecipeName()
	{
		return I18n.format("gui.stonecutter.title");
	}

	@Override
	public String getGuiTexture()
	{
		return ResAdditae.ident("textures/gui/nei/stonecutter.png");
	}
	
	@Override
	public int recipiesPerPage()
	{
		return 4;
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		ItemStack ingredient = StonecutterRecipes.getInputFromOutput(result);
		if (ingredient == null) {
			return;
		}
		arecipes.add(new CachedStonecutterRecipe(ingredient, result));
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient)
	{
		ItemStack[] results = StonecutterRecipes.getOutputsFromInput(ingredient);
		for (ItemStack result : results) {
			arecipes.add(new CachedStonecutterRecipe(ingredient, result));
		}
	}
	
	public class CachedStonecutterRecipe extends TemplateRecipeHandler.CachedRecipe
	{
		private PositionedStack ingredient;
		private PositionedStack result;
		
		public CachedStonecutterRecipe(ItemStack ingredient, ItemStack result)
		{
			this.ingredient = new PositionedStack(ingredient.copy(), 50 - 5, 17 - 11);
			this.result = new PositionedStack(result.copy(), 112 - 5, 17 - 11);
		}
		
		@Override
		public List<PositionedStack> getIngredients()
		{
			List<PositionedStack> l = new ArrayList<PositionedStack>();
			l.add(this.ingredient);
			return l;
		}
		
		@Override
		public PositionedStack getResult()
		{
			return this.result;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof CachedStonecutterRecipe)) {
				return false;
			}
			
			CachedStonecutterRecipe other = (CachedStonecutterRecipe) obj;
			if (other.ingredient == null && this.ingredient != null) {
				return false;
			}
			if (other.ingredient != null && this.ingredient == null) {
				return false;
			}
			if (!ItemStack.areItemStacksEqual(other.ingredient.item, this.ingredient.item)) {
				return false;
			}
			
			if (other.result == null && this.result != null) {
				return false;
			}
			if (other.result != null && this.result == null) {
				return false;
			}
			if (!ItemStack.areItemStacksEqual(other.result.item, this.result.item)) {
				return false;
			}
			
			return true;
		}
	}
}
