document.getElementsByClassName("color_palette").onclick = changeColor;

function changeColor() {
    document.getElementsByClassName("color_palette").style.borderStyle = 'inset';
    return false;
}


function dropdownMenu() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function (event) { // dropdown menu
    if (!event.target.matches('.dropbtn')) {
        let dropdowns = document.getElementsByClassName("dropdown-content");
        let i;
        for (i = 0; i < dropdowns.length; i++) {
            let openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

function highlightPalette(){
    document.getElementById("colorPalette").style.backgroundColor = "red";
}

function changeBack(divId){
    if(divId === "colorPalette"){
        document.getElementById(divId).style.backgroundColor = "lightblue";
    }
    else {
        document.getElementById(divId).style.backgroundColor = "lightblue";
    }

}

function generateField(){ // hides howToPlay and shows sudoku field
    if (window.location.href !== "http://localhost:8080/colorsudoku/new"){
        document.getElementById("howToPlay").style.display = "none";
        document.getElementById("sud").style.display = "block";
        document.getElementById("colorPalette").style.pointerEvents = "auto";
        document.getElementById("operation").style.pointerEvents = "auto";
    }

}
window.onload = function (){
    let regex = /(.*\/colorsudoku\/new.*)/g;
    let url = window.location.href;
    if (url.match(regex)) {
        document.getElementById("howToPlay").style.display = "block";
        document.getElementById("sud").style.display = "none";
        document.getElementById("colorPalette").style.pointerEvents = "none";
        document.getElementById("operation").style.pointerEvents = "none";
    }

}


function highlightBackground(divId){
    document.getElementById(divId).style.backgroundColor = "red";

}

function showCommentForm(){
    if (document.getElementById("comment").style.display === "none"){
        if (document.getElementById("stars").style.display === "block"){
            document.getElementById("stars").style.display = "none";
        }
        document.getElementById("comment").style.display = "flex";
        document.getElementById("comment").style.flexDirection = "column";
    }
    else {
        document.getElementById("comment").style.display = "none";
    }


}


function rateMyGame(){
    if (document.getElementById("stars").style.display === "none"){
        if (document.getElementById("comment").style.display === "flex"){
            document.getElementById("comment").style.display = "none";
        }
        document.getElementById("stars").style.display = "block";
    }
    else {
        document.getElementById("stars").style.display = "none";
    }
}
