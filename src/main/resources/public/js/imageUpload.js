function imageUploaded() {
    document.getElementById("imgTest").innerHTML='';
    var files = document.getElementById("formFileMultiple").files;

    var count = document.createElement('textarea');
    count.classList = "d-none form-control";
    count.value= files.length;
    count.innerHTML= files.length;
    count.id="imagesCount";
    count.name="imagesCount";
    count.type = "text";

    document.getElementById("imgTest").innerHTML += count.outerHTML;

    for (i = 0; i < files.length; i++){
        if(files[i].size > 60000000)
        {
            alert("File too big!");
            //upl.value = "";
        } else{
            loadImageFileAsURL(files[i], i);
        }
    }
}

function loadImageFileAsURL(fileToLoad, i){
    var fileReader = new FileReader();

    fileReader.onload = function(fileLoadedEvent) {
        var srcData = fileLoadedEvent.target.result; // <--- data: base64
        var divTest = document.getElementById("imgTest");
        var newImage = document.createElement('textarea');
        newImage.value = srcData;
        newImage.innerHTML = srcData;
        newImage.type = "text";
        newImage.id="textIn"+i;
        newImage.classList = "d-none form-control";

        divTest.innerHTML += newImage.outerHTML;
        console.log(srcData)

    }

    fileReader.readAsDataURL(fileToLoad);
}