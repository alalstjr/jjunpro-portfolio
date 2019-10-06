import React, { Component, Fragment } from 'react';
import { Link } from "react-router-dom";
import PropTypes from "prop-types";

import { ListBg, ListBtnWrap } from "../../style";
import { Container, WriteBtn } from "../../../../style/globalStyles";

class ListBtn extends Component {
    render() {

        // props Init
        const { option } = this.props;

        // Variables Init
        let BtnContent;

        // List Btn
        const listBtn = () => {
            return(
                <ListBg>
                    <ListBtnWrap>
                        <Container>
                            <Link to="/boardEvent/write">
                                <WriteBtn>
                                    글쓰기
                                </WriteBtn>
                            </Link>
                        </Container>
                    </ListBtnWrap>
                </ListBg>
            );
        };

        // Write Btn
        const writeBtn = () => {
            return(
                <ListBtnWrap>
                    <Container>
                        <WriteBtn type="submit">
                            작성 완료
                        </WriteBtn>
                    </Container>
                </ListBtnWrap>
            );
        };

        switch(option) {
            case "write" :
                BtnContent = writeBtn();
                break;
            default :
                BtnContent = listBtn();
                break;
        }

        return (
            <Fragment>
                {BtnContent}
            </Fragment>
        )
    }
}


ListBtn.propTypes = {
    option: PropTypes.string.isRequired
}

export default ListBtn;