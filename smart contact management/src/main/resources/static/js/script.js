


let currentTheme = getTheme();


document.addEventListener('DOMContentLoaded',()=>{
    changeTheme()
})



// TODO
function changeTheme() {
    //  set to web page
    // document.querySelector("html").classList.add(currentTheme);


    changePageTheme(currentTheme,currentTheme);


    // set the listner to change theme button
    const changeThemeButton = document.querySelector("#theme_change_button");
    const oldTheme = currentTheme;

    changeThemeButton.addEventListener("click", (event) => {


        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";

        }


        changePageTheme(currentTheme,oldTheme)
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

function changePageTheme(theme,oldTheme) {
    
    // update in locat storage
    setTheme(currentTheme);
    //remove current theme 
    document.querySelector("html").classList.remove(oldTheme);
    // set current theme
    document.querySelector("html").classList.add(theme);
    // change text on the botton
    document.querySelector('#theme_change_button').querySelector('span').textContent = theme == "light" ? "dark" : "light";

}