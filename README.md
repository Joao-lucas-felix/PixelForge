# PixelForge

<p align="center">
  <img src="Assets/Logo/android-chrome-192x192.png" alt="Logo do Projeto Pixel Forge Versão Inicial" width="200">
</p>

A site for gamedevs and artists to post and consume pixel arts. To be able to find tilesets, sprites, animations, concept arts and more.

Project Pixel Forge. 

## Project Pixel Forge

Now the project has a CRUD ready for pixel arts, the CRUD has the following functions:

- A endpoint to Create a pixel art, that endpoint needs 4 infromations:
    - The file of pixel art - File field
    - The name of pixel art - Text field
    - The description of pixel art  - Text field
    - An field thats informs if the pixel art are free to use or not - Boolean field
    
    to use this Method should be access the path `/api/pixel-art/v1`  using the method post, and a Form-data with the data.  And this endpoint response  an Json in this format: 
    
    ```json
    {
        "pixelArt": {
            "key": 1,
            "name": "Example Name",
            "description": "Example Description",
            "originalFileName": "exampleFile.png",
            "isFreeUse": true,
            "userName": "user",
            "links": [
                {
                    "rel": "Link to load this file",
                    "href": "http://localhost:8080/api/pixel-art/v1/downloadFile/exampleFile.png"
                }
            ]
        },
        "fileInfo": {
            "fileName": "exampleFile.png",
            "fileDownloadUri": "http://localhost:8080/api/file/v1/download/exampleFile.png",
            "fileType": "image/png",
            "fileSize": 2020
        }
    }
    ```
    
- The endpoint of update a pixel art with the file  in the path`/api/pixel-art/v1/{id}/file` , using the PUT method. The {id} represents the id of your pixel art in the API that function receives an Form-data too.  And this endpoint response  an Json in this format:

```json
{
    "pixelArt": {
        "key": 1,
        "name": "Example Name",
        "description": "Example Description",
        "originalFileName": "exampleFile.png",
        "isFreeUse": true,
        "userName": "user",
        "links": [
            {
                "rel": "Link to load this file",
                "href": "http://localhost:8080/api/pixel-art/v1/downloadFile/exampleFile.png"
            }
        ]
    },
    "fileInfo": {
        "fileName": "exampleFile.png",
        "fileDownloadUri": "http://localhost:8080/api/file/v1/download/exampleFile.png",
        "fileType": "image/png",
        "fileSize": 2020
    }
}
```

- The function of update a pixel art withou file in the path `/api/pixel-art/v1/{id}` , using the put method. That endpoint receive a json with the data wihtout the file.  And this endpoint response  an Json in this format:

```json
{
    "key": 2,
    "name": "Tileset Floresta update test ",
    "description": "Update test ",
    "originalFileName": "tileset_floresta.png",
    "isFreeUse": false,
    "userName": "admin2",
    "_links": {
        "Link to load this file": {
            "href": "http://localhost:8080/api/pixel-art/v1/downloadFile/tileset_floresta.png"
        },
        "self": {
            "href": "http://localhost:8080/api/pixel-art/v1/2"
        }
    }
}
```
 

Done:

- [x]  Understand how to map many entities to one, in the sense that a user will be the owner of many arts.
- [x]  Implement these entities. Pattern Idea: manege the encrypted file path, so as not to expose details of the API.

- Observation, i think it's not needed encrypt the file path beacouse we gonna
trafic the file over the network.

- [x]  Create the Excpetion Handler, and customized exceptions.
- [x]  Create the endpoint that you manage as Pixel Arts, and decide how you will organize the creation of files, the idea is that an API is the storage, so in the beginning, there is no reason to resort to a third-party storage service.
- [x]  Implements the get functions in the endpoint ( implment the method "get by name") 
- [X]  Implementes the update functions in the endpoint
- [X]  Implements the delete function in the endpoint
To Do: 
- []  Writes the tests to the pixel art endpoints, the security endpoints

## Ideas:

- Create a color pallete to the pixel arts when register:
- Creates thubnails to the pixel arts.

How i can open and process images in java: 

- To open the image: 
`BufferedImage image = ImageIO.read(fileOrInputStreamOrURL)`

### WireFrames:
#### Login Flux initial concept:
<p align="center">
  <img src="Assets/Wireframes/wirefram-login-flux.png" alt="Wirefram of initial login flux" width="800" height="600">
</p>
