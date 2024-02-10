# Simple Aged Cache

This is my implimentation of the "simple aged cache" from "Fundamental of Software Architecture for Big Data" class by Mike Barinek and Tyson Gern, lecturers in the Computer Science program at University of Colorado Boulder.  This is my reflection on the exercise for educational purposes.

## Setup

I added the repository to my local hardrive from a zip file provied in the coursework.  I the created this remote repository of my own for version control.  The project uses `Java` with `IntelliJ IDEA`.  The initial repo from class included the `.github` workflow - `build.yml`.  It also included `gradle` to automate the application build.  In addition, the `JUnit` test suite with 4 failing tests came with the project.  The objective was to build the SimpleAgedCache in Java and/or Kotlin.  I chose Java for this first iteration.

## Implementation 

This is a "simple" aged cache, which in my case means it can hold data in a key-value store using Arrays, each entry having an expiration time.  Adding capability for other data types would make this less "simple", but is something for a future iteration. 

I imported the Arrays utility `import java.util.Arrays;` in order to accomplish this task.  There are two constants defined right away.  `INITIAL_CAPACITY` holds is a default value of 10, and `GROWTH_FACTOR` which is used in the `.put` method to increase the storage capacity of the array by 2 when necessary. 

The constructors have private instance variables that include clock (imported), keys (array), values (array), expirationTimes (array), and size (integer).  A helper method was created and called in the `.put` method in order to `ensureCapacity`, and was responsible for checking the length of the Arrays via `keys.length`, and increasing the capacity by `GROWTH_FACTOR` if more space was needed - increasing the default array size by a factor of 2.  After checking if the cache has the storage capacity, the `.put` method adds the arguments passed in for key, value, and expiration time to the Arrays, then it increments the size by one.

`isEmpty()` is a boolean method that will return true only if size is equal to 0. 

`size()` is a method that will return the "size" of the array, which is stored in the instance variable `size`.

`.get(Object key)` retrieves the value associated with the `key` passed in as an argument.  It uses a `for` loop to iterate through the keys array until it finds the key that matches the key passed in as an argument.  Then, it checks if the key is expired.  If the key is not expired, it returns the value associated with that key.  If it is expired, it removes the key and value, resets the expiration time, decrements the size, and returns null.  

This implementation required me to modify the JUnit for the method - adding one necessary line to call the `.get` method in order to removed the entry with the expired time.  This was my solution to removing the expired item and in hindsight I do not think it is the best solution.  

In a future PR, I will remove the functionality from `.get` method that checks for expired key, since it may not be necessary there.  Instead, I will create a subclass called `ExpiredEntries` which will use a scheduler to periodically check the array for expired keys and removed them. 

## Java

This is my first school based exercise I have done that required Java.  I have previous experience using Java in my coding bootcamp rebuilding Battleship, which I initially build using Ruby. Aside from obvious differences in syntax (setting up public and private variables/methods explicitly, `{}` and `;` - `def`, `end` etc.), Java being much more "verbose", it took me a minute to get used to the differences around constructing a class, declaring data types (Java is "statically typed"), and really just navigating IntilliJ IDEA.  

I found that Ruby translates well to Java in many ways, both being Object Oriented Programming languages.  After figuring out how to read the error messages and setting up break points, I started to see OOP principles and the exercise clicked.  It helps me to think of the computer as being "dumb" - it doesn't know what to do until I tell it what to do.  Ruby is a bit more forgiving.  You do not have to explicity state things as much.  However with Java, as I learned through this exercise, you have to be much more explicit - a good thing for this stage of growth.

# Credits

 @barinek

© 2021 by Initial Capacity, Inc. All rights reserved.

