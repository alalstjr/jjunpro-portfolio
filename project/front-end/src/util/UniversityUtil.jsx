export const setAtmosphere = (uniAtmosphere) => {
    switch(uniAtmosphere) {
        case 1:
            return "조용한 분위기";
        case 2:
            return "고급스런 분위기";
        case 3: 
            return "활발한 분위기";
        default :
            return ""
    }
}

export const setPrice = (uniPrice) => {
    switch(uniPrice) {
        case 1:
            return "많이 비싸다";
        case 2:
            return "조금 비싸다";
        case 3: 
            return "보통 가격대";
        case 4: 
            return "저렴하다";
        case 5: 
            return "많이 저렴하다";
        default :
            return ""
    }
}