const reset = `
    * { 
        -webkit-font-smoothing:antialiased;
        font-smoothing:antialiased;
    }
    body, form, div, p, h1, h2, h3, h4, h5, h6, dl, dt, dd, ul, ol, li, fieldset, th, td, input, textarea,button,select { 
        margin:0; padding:0;
    }
    article,aside,details,figcaption,figure,footer,header,hgroup,nav,section,summary { 
        display:block; 
        margin:0; 
        padding:0; 
    }
    img,fieldset,button { 
        border:0 none; 
    }
    table {
        border-spacing:0;
    }
    ol, ul, dl { 
        list-style:none; 
    }
    li { 
        list-style:none; 
    }
    a { 
        text-decoration:none; 
        color: #333;
    }
    img{ 
        vertical-align:top
    }
    address, caption, em, var { 
        font-style:normal; 
        font-weight:normal; 
    }
    input, textarea, select{ 
        letter-spacing:normal; 
    }
    button { 
        border:0; 
        background:none; 
        cursor:pointer; 
    }
`;

export default reset;