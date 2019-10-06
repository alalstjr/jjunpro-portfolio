import React, { Component, Fragment } from 'react';
import { Link } from "react-router-dom";

import { PagingWrap } from "../../style";

class Paging extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lineFirst: 0,    // 페이징 맨 왼쪽 처음 변수
            lineLast: 0,     // 페이징 맨 마지막 변수
            pageCount: 5,     // 보여지는 페이징 번호 갯수
            currentPage: this.props.pageable.pageNumber + 1 // 현재 페이지 값
        }
    }

    componentWillMount() {
        // 페이징 초기값 설정
        this.pagingControler();
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.pageable !== undefined) {
            // 페이징 실행될때 함수 실행
            this.pagingControler();
        }
    }

    pagingControler = () => {
        // state Init
        const { 
            pageCount
        } = this.state;

        const {
            pageNumber
        } = this.props.pageable;

        this.setState({
            currentPage: pageNumber+1,
            lineFirst: Math.round((pageNumber+1)-(pageCount/2)),
            lineLast: Math.floor((pageNumber+1)+(pageCount/2))
        });
    }

    render() {

        // props Init
        const {
            first,
            last,
            totalPages,
            getPaging
        } = this.props;
        
        // state Init
        const {
            lineLast,
            lineFirst,
            currentPage
        } = this.state;

        // Variables Init
        let PagingContent;
        let pagings = [];
        let next;
        let prev;
        let pageChild;

        const PagingGetList = () => {
            // 게시물 페이징
            /*
             * lineFirst 가장 왼쪽끝의 시작번호를 의미  
             * lineLast 표시할 페이징의 갯수를 의미
             * ex) lineFirst 는 (1 2 3 4 5) 가 있다면 1을 의미
             */
            for(var i = lineFirst; i <= lineLast; i++) {
                if(i > 0 && i <= totalPages) {
                    if(currentPage !== i) {
                        pageChild = <Link key={i} to={`/boardEvent/${i}`} onClick={getPaging.bind(this, i-1)}>{i}</Link>;
                    } else {
                        pageChild = <span key={i}>{i}</span>
                    }

                    pagings.push(
                        pageChild
                    );
                }
            }

            // 이전 게시물 이동 버튼
            prev = !first ? <Link to={`/boardEvent/${currentPage-1}`} onClick={() => getPaging(currentPage-2)}>이전</Link> : null;

            // 다음 게시물 이동 버튼
            next = !last ? <Link to={`/boardEvent/${currentPage+1}`} onClick={() => getPaging(currentPage)}>다음</Link> : null;

            return(
                <Fragment>
                    {prev}
                    {pagings}
                    {next}
                </Fragment>
            );
        }

        // 최종 result 변수에 담아서 사용하도록 함수를 담아줍니다.
        PagingContent = PagingGetList();

        return (
            <PagingWrap>
                {PagingContent}
            </PagingWrap>
        )
    }
}

export default Paging;