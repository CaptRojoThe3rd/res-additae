
import cv2
import numpy as np
import pygame

paths = [
	"/0/jet_black.png",
	"/0/charcoal.png",
	"/0/shadow_gray.png",
	"/0/smoke_gray.png",
	"/0/pearl_gray.png",
	"/0/warm_gray.png",
	"/0/graphite.png",
	"/0/flint.png",
	"/1/mahogany.png",
	"/1/maroon.png",
	"/1/wine.png",
	"/1/redwood.png",
	"/1/indian_red.png",
	"/1/salmon.png",
	"/1/vermilion.png",
	"/1/scarlet.png",
	"/2/umber.png",
	"/2/rust.png",
	"/2/fulvous.png",
	"/2/coral.png",
	"/2/carrot_orange.png",
	"/2/cantaloupe.png",
	"/2/apricot.png",
	"/2/papaya_whip.png",
	"/3/mellow_yellow.png",
	"/3/cyber_yellow.png",
	"/3/sand.png",
	"/3/tan.png",
	"/3/sepia.png",
	"/3/lemon.png",
	"/3/green_yellow.png",
	"/3/chartreuse.png",
	"/4/dark_green.png",
	"/4/army_green.png",
	"/4/sheen_green.png",
	"/4/sea_green.png",
	"/4/kelly_green.png",
	"/4/spring_green.png",
	"/4/tea_green.png",
	"/4/sage_green.png",
	"/5/prussian_blue.png",
	"/5/aegean.png",
	"/5/zydeco.png",
	"/5/turkish_blue.png",
	"/5/turquoise.png",
	"/5/aquamarine.png",
	"/5/celeste.png",
	"/5/pewter_blue.png",
	"/6/midnight_frost.png",
	"/6/night_blue.png",
	"/6/navy_blue.png",
	"/6/cerulean.png",
	"/6/steel_blue.png",
	"/6/independence_blue.png",
	"/6/picotee_blue.png",
	"/6/ultramarine.png",
	"/7/indigo.png",
	"/7/deep_purple.png",
	"/7/raisin.png",
	"/7/royal_purple.png",
	"/7/medium_purple.png",
	"/7/iris.png",
	"/7/periwinkle.png",
	"/7/thistle.png",
	"/8/berry_magenta.png",
	"/8/byzantine.png",
	"/8/mulberry.png",
	"/8/rose.png",
	"/8/dusty_pink.png",
	"/8/thulian_pink.png",
	"/8/flamingo_pink.png",
	"/8/light_orchid.png"
]

def main():
	pygame.init()

	input = "to_colorize.png"
	div = 156

	for p in paths:
		dye = pygame.image.load("dye" + p)
		color = dye.get_at((8, 8))
		r = color.r / 256
		g = color.g / 256
		b = color.b / 256
		print(f"{{{r:.2f}f, {g:.2f}f, {b:.2f}f}},")

if __name__ == "__main__":
	main()