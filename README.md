<a name="readme-top"></a>

<h1 align="left"> My Music Project's API </h1>
<br>

<p align="center">
   <img src="http://img.shields.io/static/v1?label=STATUS&message=UNDER%20DEVELOPMENT&color=RED&style=for-the-badge"/>
</p>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#features">Features</a></li>
      </ul>
       <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#routes">Routes</a></li>
    <li><a href="#Contat">Contact</a></li>
  </ol>
</details>
<br>
<hr>


<!-- ABOUT THE PROJECT -->
## About The Project

It's an API that allows users to search for his favorites artists and musics and save them into a personal playlist.

<br>
<br>




### Features
- [x]  Allow the user to search for new music in the database: 
- [ ]  Allow the user to pick a song from and add it to his playlist; 
- [ ]  Allow the user to remove a song his playlist: 
- [ ]  Create two types of users: Premium and common.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<br>
<br>


### Built With

* Java
* Maven
* Spring Boot
* SQLite
* Docker


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.
<br>

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Docker
<br>
You can download Docker to Mac, Windows or Linux [here](https://www.docker.com/get-started/#h_installation).

<br>


### Installation

_With the docker installed and running you can follow the next steps:_

1. Clone the repo
   ```sh
   git clone https://github.com/rebsviana/my-music-coffee
   ```
2. Run maven 
   ```sh
   cd my-music-cofee
   mvn clean install
   ```
4. Run the project's image
   ```js
   docker run -p 8081:8080 --rm my-music
   ```

5. Use a browser of your choice and open the URL:

         http://localhost:8081/my-music/api/v1

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Routes

In this space will be showed examples of the routes that are usable on this API

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- CONTACT -->
## Contact


Project Link: [https://github.com/rebsviana/my-music-coffee](https://github.com/rebsviana/my-music-coffee)

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars3.githubusercontent.com/u/31936044" width="100px;" alt="Foto do Iuri Silva no GitHub"/><br>
        <sub>
          <b>Claiver Carmo</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://s2.glbimg.com/FUcw2usZfSTL6yCCGj3L3v3SpJ8=/smart/e.glbimg.com/og/ed/f/original/2019/04/25/zuckerberg_podcast.jpg" width="100px;" alt="Foto do Mark Zuckerberg"/><br>
        <sub>
          <b>Luana Lima</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://media.seudinheiro.com/cdn-cgi/image/fit=contain,width=640&,format=auto/uploads/2022/08/lebron-jams-nba-lakers-rep-1-628x353.jpg" width=100px; alt="Foto do Steve Jobs"/><br>
        <sub>
          <b>Peter Leite</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://user-images.githubusercontent.com/108896697/187672107-20aa04b1-2478-4396-862a-defcd86d1e26.jpeg" width="100px;" alt="Foto da Rebeca Baptista"/><br>
        <sub>
          <b>Rebeca Baptista</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

<p align="right">(<a href="#readme-top">back to top</a>)</p>


