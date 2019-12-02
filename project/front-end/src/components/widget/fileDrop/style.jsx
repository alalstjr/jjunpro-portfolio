import styled from 'styled-components';

export const DropZoneWrap = styled.div`
    padding: 10px;
    border-radius: 15px;
    background-color: #edf0f4;  
`;
export const DropZone = styled.div`
    padding: 20px 0; 
    font-size: 16px;
    text-align: center;
`;
export const FileList = styled.li`
    color: #fff;
    padding: 10px 15px;
    border-radius: 7px;
    background-color: ${props => props.theme.themeColor};
    margin-bottom: 5px;
    position: relative;
    &:last-child {
        margin-bottom: 0px;
    }
`;
export const FileRemove = styled.button`
    width: 25px;
    height: 25px;
    border-radius: 50%;
    background-color: rgba(0,0,0,0.5);
    position: absolute;
    right: 10px;
    top: 8px;

    &:after,
    &:before {
        content: "";
        display: block;
        position: absolute;
        background-color: #fff;
        transform: rotate(43deg);
    }
    &:after {
        width: 16px;
        height: 2px;
        right: 4px;
        top: 12px;
    }
    &:before {
        height: 16px;
        width: 2px;
        right: 11px;
        top: 5px;
    }
`;