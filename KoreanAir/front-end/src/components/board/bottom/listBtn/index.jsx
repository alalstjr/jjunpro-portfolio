import React, { Component, Fragment } from 'react';
import { Link } from "react-router-dom";
import PropTypes from "prop-types";

import { ListBg, ListBtnWrap } from "../../style";
import { Container, Btn } from "../../../../style/globalStyles";

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
                                <Btn>
                                    글쓰기
                                </Btn>
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
                        <Link to="/boardEvent/write">
                            <Btn>
                                작성 완료
                            </Btn>
                        </Link>
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