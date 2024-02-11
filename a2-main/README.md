# A2

There are no special requirements needed to load this. 

Download the repository
mvm clean test jacoco:report

I used Json.org. I chose Json.org over Gson and Jackson. I have used it for parsing Json files before and I know that it is simple. 

| Jackson | Json.org  | Gson  |
| ------- | --- | --- |
| 1.5mb | 69kb | 243kb |

Jackson and Gson provide more functionality than Json.org. For example Gson allows you deserialise classes and Jackson allows you to parse xml as well. Since these functionalities are not required. Json provides a light weight alternitive. 

| Jackson | Json.org  | Gson  |
| ------- | --- | --- |
| 3 | 0 | 0 |

Jackson has vulnerablities in 3/5 latest versions. These recent versions have less errors than past versions. Json and Gson don't have known vulnerablities in any of their versions.

| Jackson | Json.org  | Gson  |
| ------- | --- | --- |
| 22,119 | 4,733 | 17,999 |

Jackson and Gson are by far the most used. This is further displayed in their stack overflow reviews. Everyone recommends Jackson and Gson.

| Jackson | Json.org  | Gson  |
| ------- | --- | --- |
| Apache 2.0 | JSON | Apache 2.0 |

The appache Licence means that the code is open source and can be peer reviewed. Code with a JSON licence aren't open source. 

## Thoughts
I ended up choosing Json.org because of my past experience, its lack of vulnerablities in past versions and the fact that it is lightweight and I have limited storage on my computer.


