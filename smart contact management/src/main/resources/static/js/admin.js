
// preview image script
document.querySelector("#image-file-input").addEventListener("change", function(e) {

    let file = e.target.files[0];
    let reader = new FileReader();
    reader.onload = function() {
        document.querySelector("#upload-image-preview").setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);



});