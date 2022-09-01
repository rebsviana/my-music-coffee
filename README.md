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
<hr>

<!-- ABOUT THE PROJECT -->
## About The Project

It's an API that allows users to search for his favorites artists and musics and save them into a personal playlist.

<br>

### Features
- [x]  Allow the user to search for new music in the database: 
- [x]  Allow the user to pick a song from and add it to his playlist; 
- [ ]  Allow the user to remove a song his playlist: 
- [ ]  Create two types of users: Premium and common.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Built With

* ![image](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
* [![Maven Central](https://maven-badges-generator.herokuapp.com/maven-central/tech.iooo.coco/iooo-distribution-config/badge.svg?color=orange&style=flat-square)](https://maven-badges-generator.herokuapp.com/maven-central/tech.iooo.coco/iooo-distribution-config)
* ![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
* ![image](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
* ![image](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

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

### Search new music

#### Request

```
GET http://localhost:8080/api/v1/music?filtro={filtro}

Run this example: GET http://localhost:8080/api/v1/music?filtro=bruno
```

#### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
    "id": "12659604-a4a1-4c4c-8a5f-29fff1ad2ac5",
    "name": "24K Magic",
    "artistId": {
      "id": "88ac7b00-9489-49ae-a5b1-79d3ba7fc2e6",
      "name": "Bruno Mars"
    }

### Add new music to playlist

#### Request

```
POST http://localhost:8080/api/playlists/{playlistId}/musicas

Run this example: POST http://localhost:8080/api/playlists/a39926f4-6acb-4497-884f-d4e5296ef652/musicas

 {
 "id": "12659604-a4a1-4c4c-8a5f-29fff1ad2ac5",
 "name": "24K Magic",
 "artistId": {
   "id": "88ac7b00-9489-49ae-a5b1-79d3ba7fc2e6",
   "name": "Bruno Mars"
 }
```

#### Response

    HTTP/1.1 201 Created
    Status: 201 Created
    Content-Type: application/json
    Location: http://localhost:8080/api/playlists/a39926f4-6acb-4497-884f-d4e5296ef652/musicas/12659604-a4a1-4c4c-8a5f-29fff1ad2ac5

    []

_For more examples, please refer to the [Documentation](https://docs.google.com/document/d/1fEzrdsFtBViF5x-4h2T7BrSUCHZqf9nVHXdaVnIRC4Q/edit?usp=sharing)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Project Link: [https://github.com/rebsviana/my-music-coffee](https://github.com/rebsviana/my-music-coffee)

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars3.githubusercontent.com/u/31936044" width="100px;" alt="Foto do Claiver Carmo"/><br>
        <sub>
          <b>Claiver Carmo</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://user-images.githubusercontent.com/108896697/187672817-4942f97b-c2e2-4f59-9472-ea775ed14f0b.jpg" width="100px;" alt="Foto da Luana Lima"/><br>
        <sub>
          <b>Luana Lima</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://media.seudinheiro.com/cdn-cgi/image/fit=contain,width=640&,format=auto/uploads/2022/08/lebron-jams-nba-lakers-rep-1-628x353.jpg" width=100px; alt="Foto do Peter Leite"/><br>
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


