

$(document).ready(function () {
    $('#image-file-input').change(function () {
        const file = this.files[0];
        const maxSize = 3 * 1024 * 1024; // 3 MB

        if (file.size > maxSize) {
            $('#message').text('File size exceeds the maximum allowed size of 3 MB.');
            // Clear the file input
            $(this).val('');
        } else {
            $('#message').text('');
        }
    });

    // $('#uploadForm').submit(function (e) {
    //     const file = $('#image-file-input')[0].files[0];
    //     const maxSize = 3 * 1024 * 1024; // 3 MB

    //     if (file && file.size > maxSize) {
    //         e.preventDefault();
    //         $('#message').text('File size exceeds the maximum allowed size of 3 MB.');
    //     }
    // });
});









// preview image script

document.querySelector("#image-file-input").addEventListener("change", function (e) {

    let file = e.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
        document.querySelector("#upload-image-preview").setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);



});