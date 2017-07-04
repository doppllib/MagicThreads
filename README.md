MagicThreads is a threading support library for Android. It was written a few years ago to provide a simple thread queue for 'live' tasks, and a persisted thread queue for offline support. It was mostly for internal projects at touchlab. It is very similar to [android-priority-jobqueue](https://github.com/yigit/android-priority-jobqueue), and if you were looking for a persisted queue, I would recommend that one. The conceptual differences are actually pretty minor, and in fact, I wound up building this because the android-priority-jobqueue seemed at the time to be pretty complex under the hood (manual threading and whatnot). It's gone through a major rewrite and is definitely more tested and reviewed. We'll be moving to it in the future.

We are only providing this library so you'll be able to build the Droidcon sample app. I would not suggest learning it as we have moved on to use RxJava for threading and will be using the android-priority-jobqueue for persisted tasks and offline.

### What's a persisted task?

Persisted tasks allow operations to be run later, in the case where you either don't have a good network connection, the server is down, etc.
Tasks extend PersistedTask, and non-transient fields are persisted to local disk.

[Persisted Task Queue](https://github.com/touchlab/MagicThreads/blob/master/library/docs/PERSISTED_QUEUE.md)

# Doppl Fork

This is a fork of the touchlab MagicThreads library to provide tests and modifications to support
iOS development with J2objc using the [Doppl build framework](http://doppl.co/). This library is deprecated, and only
included to support building the Droidcon sample app.

## Versions

[0.9.1](https://github.com/doppllib/MagicThreads/tree/v0.9.1)

## Usage

```groovy
dependencies {
    compile 'co.touchlab:magicthreads:0.9.3'
    doppl 'co.touchlab:magicthreads:0.9.3.0'
}
```

## Status

Stable. No known memory issues. Tests passing, but need to be run in the simulator directly. Because the library manages threading back and forth to the main thread, we can't suspend the main thread to wait for execution. Will come up with a solution for other libraries with the same issue, but this one is not a priority.

## Library Development

See [docs](http://doppl.co/docs/createlibrary.html) for an overview of our setup and repo org for forked library development.

License
=======

    Copyright 2014 touchlab, Inc.

    Licensed under the Apache License, Version 2.0 (the 'License');
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an 'AS IS' BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
