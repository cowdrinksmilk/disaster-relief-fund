/**
 * Javascript File for display local changes to the app.component.html page.
 * Provides function(s) for updating onscreen appearance depending on user-type and need-selection.
 *
 *
 *  Author[s]: Demitri Clark, Emma Wheeler, Mary Almazan,
 */

isAdmin = false;

const adminElements = document.getElementsByClassName('Admin');
const loginelements = document.getElementsByClassName('loginScreen');
const usernameelements = document.getElementsByClassName('username');
const helperElements = document.getElementsByClassName('Helper');
const receiptElements = document.getElementsByClassName('Receipts');


/*
*
Login() handles the users login input and chooses what elements to display.

*/

function login(){
    console.log(adminElements);
    console.log(receiptElements);

    if( usernameelements[0].value =="Admin" || usernameelements[0].value =="admin"){
        isAdmin=true;
    }

    loginelements[0].style.display= "none";

    if(isAdmin){
        for (let i = 0; i < adminElements.length; i++) {
            adminElements[i].style.display = 'inline';
        }
    }
    else { 
        for (let i = 0; i < helperElements.length; i++) {
            helperElements[i].style.display = 'inline';
        }
    
    }
    
}

function showReceipt(){
    for(let i = 0; i < helperElements.length; i++){
        helperElements[i].style.display = 'none';
    }
    for (let i = 0; i < receiptElements.length; i++) {
        receiptElements[i].style.display = 'inline';
    }
}

function hideReceipt(){
    for(let i = 0; i < helperElements.length; i++){
        helperElements[i].style.display = 'inline';
    }
    for (let i = 0; i < receiptElements.length; i++) {
        receiptElements[i].style.display = 'none';
    }
}



