
import pygame
from os import walk
from sys import argv


def main():
	pygame.init()

	f = []
	for (dirpath, dirname, filename) in walk("stone_overlays_dark/"):
		f.extend(filename)

	image = pygame.image.load(argv[1])
	dark_level = float(argv[2])
	light_level = float(argv[3])

	for n in f:
		surf = pygame.Surface((16, 16), pygame.SRCALPHA)
		dark = pygame.image.load("stone_overlays_dark/" + n)
		dark.set_alpha(int(dark_level * 256))
		light = pygame.image.load("stone_overlays_light/" + n)
		light.set_alpha(int(light_level * 256))
		surf.blit(image, pygame.Rect(0, 0, 16, 16))
		surf.blit(dark, pygame.Rect(0, 0, 16, 16))
		surf.blit(light, pygame.Rect(0, 0, 16, 16))
		pygame.image.save(surf, "out/" + n)


if __name__ == "__main__":
	main()
