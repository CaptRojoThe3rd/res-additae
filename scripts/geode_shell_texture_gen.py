
import os
from PIL import Image

blank_image = Image.open("blank.png")
base_image = Image.open("geode_shell.png")

shiny_image_files = os.listdir(os.getcwd() + "/shiny_rocks")
shiny_images = []
for f in shiny_image_files:
	shiny_images.append(Image.open("shiny_rocks/" + f))
	
for i in range(0, len(shiny_images)):
	img_list = [
		shiny_images[i],
		shiny_images[i].transpose(Image.ROTATE_90),
		shiny_images[i].transpose(Image.ROTATE_180),
		shiny_images[i].transpose(Image.ROTATE_270),
		base_image
	]
	
	avg = base_image.copy()
	for j in range(1, len(img_list)):
		avg = Image.blend(avg, img_list[j], 1.0 / float(j + 1))
	avg.save("geode_shells/" + shiny_image_files[i])
