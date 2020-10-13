# 3DMandelbrot-Voyager
This is a program that provides a way to navigate between 3D slices of the Tricomplex Mandelbrot set. This program used the java language.

## Description of the program

The main interface provides many parameters that you can modify to create your 3D fractal. Here are some explanations of each parts.

### Tabs
The main tabs enables you to proceed with some actions : 

 - File : By clicking on this tab, you will access to the following options.	
 	- **Save** : save the image in two ways : save the current image or save all images in memory. It also gives you the opportunity to save the parameters that you put in the *parameters and Characteristics saves* in the main window.
 	- **Quit** : quit the program.
- Edit : By clicking on this tab, you will access to the following options.
	- **Restore parameters** : restore all the parameters to the initial state (when the application was launch).
	- **Set appropriate parameters** : set appropriate parameters for the slice you've choosen.
	- **Setting** : enables you to change the settings of the program. It is not yet configure.
	- **View process** : choose the way you want the image to be rendered (progressively or at the end of the process).
	- **Adds-on** : enables the panel for the rotation of the fractal.
- Action : this is where the action all takes place!
	- **Generate** : generate a single 3D fractal with respect to the parameters that you chose from the panels.
	- **Octaedre** : generate the 3D fractal corresponding to the octahedron the the Tricomplex MAndelbrot set (not working well).
	- **Rotation seq.** : generate a sequence of 3D fractals from a start rotation to an end rotation (this will prompt a window where you select the parameters).
	- **Slice sequence** : generate a sequence of 3D fractals from a start 3D slice to an end 3D slice (this will also prompt a window where you select the parameters).
	- **Stop generating** : stops the process.
	- **Dist. Est.** : use the distance estimator algorithm to generate the 3D fractals.
- Show : by clicking on this tab, you can see the image in its full resolution.
