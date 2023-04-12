# planmeca-worktest documentation 12.04.2023
## Juuso Ihatsu

The program I wrote creates a new raw image in the “lib” -folder, “imputedDenoise.raw” with the specified errors fixed.
My time ran short while working, so I had to forget about creating a GUI, which I was hoping to do as well. I was also new to using Gradle, and have only worked with Maven, so I hope that the project is in some satisfactory state.

The largest problem I encountered was manipulating the bytes in the functions, but in the end, I managed to get an image that had no noticeable pixels that were present in the originals. Other problems that I had were with Gradle, which for some reason confused me greatly. I used Eclipse working on the project and for some reason or another had difficulties creating a build with Gradle. I honestly ran out of time trying to solve these little issues, so I decided to instead upload what I had to GitHub and call it a day. I will study more in the future and try to get a better grasp on things.

There is much to improve in the code, since time restraints made me work hastily, so commenting and coherent code might be lacking.
I appreciate all feedback and criticism in order to learn and grow as a developer.

### Path to the java file containing main.java: PlanmecaWorktestGradle\lib\src\main\java\main.java
### Path to resources with the three raw images: PlanmecaWorktestGradle\lib\src\main\resources
![planmeca-imputed-denoise](https://user-images.githubusercontent.com/64533217/231560532-4918b126-f5ca-440d-a772-0654545cab6e.png)
