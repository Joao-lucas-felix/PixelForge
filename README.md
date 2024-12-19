# PixelForge

<p align="center">
  <img src="Assets/Logo/android-chrome-192x192.png" alt="Logo do Projeto Pixel Forge Versão Inicial" width="200">
</p>

A site for gamedevs and artists to post and consume pixel arts. To be able to find tilesets, sprites, animations, concept arts and more.


## Project Pixel Forge

### Greets API

You can access the `/hello`  to verify if the API is active. If the API is active, it will greet you.

### Getting pixel arts:

Now you can access the API Using the endpoint  `/api/pixel-art/v1` to get pixel arts: 

- Using the GET method on the `/api/pixel-art/v1` endpoint, the API will respond with a paginated JSON:

```json
{
    "_embedded": {
        "pixelArtDtoList": [
            {
                "key": 1,
                "name": "Example name",
                "description": "Example description",
                "originalFileName": "example.png",
                "isFreeUse": true,
                "userName": "exampleUser",
                "_links": {
                    "Link to load this file": {
                        "href": "http://localhost:8080/api/pixel-art/v1/downloadFile/example.png"
                    },
                    "self": {
                        "href": "http://localhost:8080/api/pixel-art/v1/1"
                    }
                }
            },
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/pixel-art/v1?page=0&size=6&direction=asc"
        }
    },
    "page": {
        "size": 6,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
    }
}
```

- Access the /api/pixel-art/v1/{id} endpoint to get a specific pixel art, the API response looks like this:

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

- You can find a pixel art by name using the /api/pixel-art/v1/searchByName/{name} endpoint. The response looks like this:

```json
{
    "_embedded": {
        "pixelArtDtoList": [
            {
                "key": 5,
                "name": "Tileset Floresta common adminTes",
                "description": "Um tile set de poucos bytes para ser usado em um cenario de floresta",
                "originalFileName": "tileset_floresta.png",
                "isFreeUse": true,
                "userName": "admin2",
                "_links": {
                    "Link to load this file": {
                        "href": "http://localhost:8080/api/pixel-art/v1/downloadFile/tileset_floresta.png"
                    },
                    "self": {
                        "href": "http://localhost:8080/api/pixel-art/v1/5"
                    }
                }
            },
            {
                "key": 6,
                "name": "Tileset Floresta common adminTes",
                "description": "Um tile set de poucos bytes para ser usado em um cenario de floresta",
                "originalFileName": "tileset_floresta.png",
                "isFreeUse": true,
                "userName": "admin2",
                "_links": {
                    "Link to load this file": {
                        "href": "http://localhost:8080/api/pixel-art/v1/downloadFile/tileset_floresta.png"
                    },
                    "self": {
                        "href": "http://localhost:8080/api/pixel-art/v1/6"
                    }
                }
            },
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
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/pixel-art/v1?page=0&size=6&direction=asc"
        }
    },
    "page": {
        "size": 6,
        "totalElements": 3,
        "totalPages": 1,
        "number": 0
    }
}
```

- And finally you can go to /api/pixel-art/v1/download File/{filename} to get the pixel art file.

### Authenticating to the API:

To create and manipulate the pixel arts you need to be authenticated

- To login in the API you acess the endpoint `/auth/login` with the body.

```json
{ 
  "username": "you_user_name",
  "password": "your_password"
}
```

```json
{
    "username": "a3651ba0-3c24-445f-9353-eca79f499a0f",
    "authenticated": true,
    "created": "2024-12-19T16:05:33.211+00:00",
    "expiration": "2024-12-19T17:05:33.211+00:00",
    "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJwaXhlbGZvcmdlIiwic3ViIjoiYTM2NTFiYTAtM2MyNC00NDVmLTkzNTMtZWNhNzlmNDk5YTBmIiwiZXhwIjoxNzM0NjI3OTMzLCJpYXQiOjE3MzQ2MjQzMzN9.kQkJL0nwdw4cglyxTx3HYSfC3iOWZYiybO4ATdMIFWhEa1Ff6LvnkQ1M-CTmYqotCe8LS58EOLTDesrxhVmwzf7FGviE7jmYTKBAtXjwTa1Y4U1bNPbkQ1AD5W_9Aw0s7Z8BqIK5ppsU0kKOneDTUFj57Tn4TLHRMpRAlte0k6Edd2mh1bI3ySZS237Ih6mi_KlDwnJhXQVJiv7_QxaGgVCO8jlvCtSgnnSVG-L0iif8rsZ6m2zf-zhFHIuL6yh1wcVxjF6TqnejjptepK6IEvIZynaycI6VRf_3XDphKuiQzUJ5pnRmplGwCsWnT3Gb53JXvP7DF8KqRIAL_7g8Zw"
}
```

### Manipuling Pixel arts

Now the project has a CRUD ready for pixel arts, the CRUD has the following functions:

- A endpoint to Create a pixel art, that endpoint needs 4 infromations:
    - The file of pixel art - File field
    - The name of pixel art - Text field
    - The description of pixel art  - Text field
    - An field thats informs if the pixel art are free to use or not - Boolean field
    
    to use this Method should be access the path `/api/pixel-art/v1`  using the method POST, and a Form-data with the data.  And this endpoint response  an Json in this format: 
    
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

- The function of update a pixel art withou file in the path `/api/pixel-art/v1/{id}` , using the PUT method. That endpoint receive a json with the data wihtout the file.  And this endpoint response  an Json in this format:

```json
{
    "key": 2,
    "name": "Example",
    "description": "Example",
    "originalFileName": "Example.png",
    "isFreeUse": false,
    "userName": "user",
    "_links": {
        "Link to load this file": {
            "href": "http://localhost:8080/api/pixel-art/v1/downloadFile/Example.png"
        },
        "self": {
            "href": "http://localhost:8080/api/pixel-art/v1/2"
        }
    }
}

```

- To delete a Pixel art you can acess:`/api/pixel-art/v1/{id}` using the method DELETE,

## TASKS
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
