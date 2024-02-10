# Simple Aged Cache

This is my implimentation of the "simple aged cache" from "Fundamental of Software Architecture for Big Data" class by Mike Barinek and Tyson Gern, lecturers in the Computer Science program at University of Colorado Boulder

## Setup

I added the repository to my local hardrive from a zip file provied in the coursework.  I the created a repository of my own for version control.  The project uses `Java` with `IntelliJ IDEA`.  The initial repo from class included the `.github` workflow - `build.yml`.  It also included `gradle` to automate the application build.  In addition, JUnit test suite with 4 failing tests came with the project.  The objective was to build the SimplelAgedCache in Java and/or Kotlin.  I chose Java due to some vague familiarity.

## Implementation 

This is a "simple" aged cache, which in my case means it hold data in a key-value store, each entry having an expiration time.  I imported the Arrays utility `import java.util.Arrays;` in order to accomplish this task.  In the There are two constants defined.  `INITIAL_CAPACITY` which is a default value of 10, and `GROWTH_FACTOR` which is used in the `.put` method to increase the storage capacity of the array when necessary. 

The constructors have private instance variables that include clock (import), keys, values, expirationTimes, and size.  A helpermethod was created and called in the `.put` method in order to `ensureCapacity`, and was responsible for checking the length of the Arrays via `keys.length`, and increasing the capacity by `GROWTH_FACTOR` if more space was needed - increasing the default array size by a factor of 2.  After checking if the cache has the storage capacity, the `.put` method increments adds the arguments passed in for key, value, and expiration time - it increments the size by one.

`isEmpty()` is a boolean method that will return true only if size is equal to 0. 

`size()` is a method that will return the "size" of the array, which is stored in the variable `size`.

`.get(Object key)` retrieves the value associated with the `key` passed in as an argument.  It uses a `for` loop to iterate through the keys until it findes the key that matches the argument.  Then, it checks if the key is expired.  If the key is not expired, it returns the value associated with that key.  If it is expired, it removes the key and value, resets the expiration time, and decrements the size - returns null.  This was my solution to removing the expired item and I do not think it is the best solution.

In my next PR, I will remove the functionality from `.get` method that checks for expired key, since it may not be necessary there.  Instead, I will create a subclass called `ExpiredEntries` which will use a scheduler to periodically check the array for expired keys and removed them. 


 @barinek

© 2021 by Initial Capacity, Inc. All rights reserved.

