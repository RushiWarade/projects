


let currentTheme = getTheme();


document.addEventListener('DOMContentLoaded', () => {
    changeTheme()
})



// TODO
function changeTheme() {
    //  set to web page
    // document.querySelector("html").classList.add(currentTheme);


    changePageTheme(currentTheme, "");


    // set the listner to change theme button
    const changeThemeButton = document.querySelector("#theme_change_button");

    changeThemeButton.addEventListener("click", (event) => {

        let oldTheme = currentTheme;

        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";

        }


        changePageTheme(currentTheme, oldTheme)
    });

}

// set theme to local storege
function setTheme(theme) {

    localStorage.setItem("theme", theme);
}



// get theme from local storage

function getTheme() {
    let theme = localStorage.getItem("theme");

    return theme ? theme : "light";
}


// change current page theme

function changePageTheme(theme, oldTheme) {

    // update in locat storage
    setTheme(currentTheme);
    //remove current theme 
    // set current theme

    if (oldTheme) {
        document.querySelector("html").classList.remove(oldTheme);
    }



    document.querySelector("html").classList.add(theme);
    // change text on the botton
    document.querySelector('#theme_change_button').querySelector('span').textContent = theme == "light" ? "dark" : "light";


    // Change the icon class
    const icon = document.querySelector("#theme_change_button").querySelector('i');
    if (theme === "light") {
        icon.classList.remove('fa-sun'); // Assuming fa-sun is the icon for light theme
        icon.classList.add('fa-circle-half-stroke'); // Assuming fa-moon is the icon for dark theme
    } else {
        icon.classList.remove('fa-circle-half-stroke'); // Assuming fa-moon is the icon for dark theme
        icon.classList.add('fa-sun'); // Assuming fa-sun is the icon for light theme
    }


}

