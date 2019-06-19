import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { CateGoryWrap, CateGoryUl } from "../style";
import { Container } from "../../../style/globalStyles";

class CateGory extends Component {
    render() {
        return (
            <CateGoryWrap>
                <Container>
                    <CateGoryUl>
                        <li>
                            <Link to="#">
                                카테고리1
                            </Link>
                        </li>
                        <li>
                            <Link to="#">
                                카테고리2
                            </Link>
                        </li>
                        <li>
                            <Link to="#">
                                카테고리3
                            </Link>
                        </li>
                    </CateGoryUl>
                </Container>
            </CateGoryWrap>
        )
    }
}

export default CateGory;