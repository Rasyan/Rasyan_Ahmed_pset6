# Rasyan_Ahmed_pset6

Welcome to the final assignment of Native App Studio

In this weeks assignment we had to make an app that uses firebase as a database and we ourselfs could choose the theme and functionality.
I decided to make an app that lets you search for recipys and then add them to your faforites list.
below is a tour of the app and its functionality

# Tour of the app

## Login screen (activity)
After opening the app you will be greeted by the login screen, the login screen is our first activity.
Here you get a short introduction to the app and by pressing the google sign in button you can login using your google account and will be send to the Home screen
Note that if you are still loged in from a previous session then you will automaticly skip this screen and go directly to the home screen.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss1.png)

## Home screen (activity)
The home activity is where you will spend the rest of your time.
this activity can call up different fragment to completly fill up the screen.
You can switch between some fragments using the swipe menu (navigation drawer) this menu can be accessed by swiping from left to right or pressing the button on the top left.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss3.png)

you can also log out of the app by pressing the logout button on the menu, this will then log you out of firebase and return you to the login screen.

### search (fragment)
the default fragment that is shown is the search fragment. 
Which shows you the recipys that are currently trending in a recycler list.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss2.png)

by pressing the floating action bar in the bottem right we can enter a search query into a Dialog fragment that pops up.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss4.png)

By pressing enter the search fragment will update itself to show your search results.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss5.png)

pressing a recipy in the list will open up the recipy fragment which will show you the full recipy.

### Recipy (fragment)
here you can see all the details of a single recipy, you can see its name, score and all the ingredients it requires.

![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss6.png)

The api itself does not show you any directions, they instead provide a link to the recipy on the authors website.
you can click the "link to ful recipe" text to open your web browser and be send to there.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss7.png)

This fragment also has a floating action button. clicking it adds the recipy to your faforite list (saved in firebase), and give a small toast as confirmation.
the fragment will then recognize that the recipy is in your faforite list and a second click on the floating action button (which is now a cross) will remove it from your faforite list again.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss8.png)

### faforite (fragment)
Using the navigation drawer we can go to the faforite list, this fragment reads from the users data on firebase and shows his faforited recipes, we can see that our previously added recipe is already shown here.
![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/ss9.png)

clicking any of the recipys will show its recipe fragment where you can view its details again or remove it from your faforites

Here ends our tour of the app.

# deadline:

on the deadline day the api was not working, and as such i could not make a tour of the app in this readme, after contacting the api they told me it would be fixed by the weekend, this was indeed true.
The text below is from the deadline day, it explains what was going on.

Currently as of 27/28 october 2016 the api i am using is heavily bugged.
It was fine up to a few days ago.
as you can see from the image below, their api will not show any images, their own site is having the same problem.
I will try to make a proper readme when the api is fully working again. or i will hardcode an example.

![example1](https://github.com/Rasyan/Rasyan_Ahmed_pset6/blob/master/doc/Naamloos.png)
