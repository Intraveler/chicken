import React from 'react';
import * as CallApiGet from '../util/CallApiGet';
import {
    BootstrapTable,
    TableHeaderColumn
} from 'react-bootstrap-table';
import '../../node_modules/react-bootstrap-table/css/react-bootstrap-table.css'

class LocationSearch extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: '',
            data: [],
            total_count: '',
            page_component: [],
            is_end: false,
            initCount: 0,
            searchButton: false,
            pageButton: false,
            click_page: 0,
            end_click_page: 0,
            endCount: 0
        };
    }

    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    callThisPage = (item) => (event) => {
        if (item === this.state.click_page) {
            return;
        }

        this.setState({click_page: item});

        CallApiGet.getLocationList(this, this.state.value, item)
            .then(data => {
                if (data === "success") {
                    if (this.state.end_click_page < item) {
                        if (!this.state.is_end) {
                            if (item < this.state.end_click_page) {
                                if (item < this.state.endCount) {
                                    var array = this.state.page_component;
                                    array.push(<button key={item + 1}
                                                       onClick={this.callThisPage(item + 1)}> {item + 1} </button>);
                                    this.setState({page_component: array});
                                }
                            } else {
                                var array = this.state.page_component;
                                array.push(<button key={item + 1}
                                                   onClick={this.callThisPage(item + 1)}> {item + 1} </button>);
                                this.setState({page_component: array});
                                this.setState({end_click_page: item});
                            }
                        } else {
                            this.setState({endCount: item});
                        }
                    }

                    this.setState({searchButton: false, pageButton: true});
                } else {

                }
            });
    }

    handleSubmit = (event) => {
        if (this.state.value !== '') {
            this.setState({click_page: 1});
            this.setState({endCount: 0});

            CallApiGet.getLocationList(this, this.state.value, 1)
                .then(data => {
                    if (data === "success") {
                        this.setState({searchButton: true, pageButton: false});

                        if (!this.state.searchButton) {
                            this.setState({initCount: 1, searchButton: true, page_component: []});

                        } else {
                            if (!this.state.pageButton) {
                                this.setState({initCount: 1, searchButton: true, page_component: []});
                            }
                        }

                        setTimeout(() => {

                            var array = this.state.page_component;
                            for (let i = 0; i <= this.state.initCount; i++) {
                                array.push(<button key={i + 1} onClick={this.callThisPage(i + 1)}> {i + 1} </button>);
                            }
                            this.setState({page_component: array});
                            this.setState({end_click_page: 1});

                        }, 1);
                    }
                });
        }

        event.preventDefault();
    }

    render() {

        return (
            <div>
                [ 장소 검색 ]
                <form onSubmit={this.handleSubmit}>
                    <label>
                        <input type="text" value={this.state.value} onChange={this.handleChange}/>
                    </label>
                    <input type="submit" value="검색하기"/>
                </form>

                {this.state.data.map((item, num) => {
                    return (<LocationSearchResult
                        address_name={item.address_name}
                        category_name={item.category_name}
                        phone={item.phone}
                        place_name={item.place_name}
                        place_url={item.place_url}
                        road_address_name={item.road_address_name}
                        id={item.id}
                        key={num}/>);
                })}

                {
                    this.state.page_component.map((item, num) => {
                        return (<Test data={item}
                                      key={num}></Test>
                        );

                    })}


                {<div><KeywordHistory></KeywordHistory></div>}
                {<div><PopulateKeyword></PopulateKeyword></div>}
            </div>
        );
    }
}

class Test extends React.Component {
    render() {

        return (
            <button> {this.props.data} </button>
        );
    }
}

class LocationSearchResult extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showPopup: false
        };
    }

    togglePopup() {
        this.setState({
            showPopup: !this.state.showPopup
        });
    }

    render() {
        return (
            <div>
                <li>
                    <button onClick={this.togglePopup.bind(this)}>{this.props.place_name}</button>
                </li>
                {this.state.showPopup ?
                    <Popup
                        addressName={this.props.address_name}
                        categoryName={this.props.category_name}
                        phone={this.props.phone}
                        placeName={this.props.place_name}
                        placeUrl={this.props.place_url}
                        roadAddressName={this.props.road_address_name}
                        id={this.props.id}
                        closePopup={this.togglePopup.bind(this)}
                    />
                    : null
                }
            </div>
        );
    }
}

class Popup extends React.Component {
    render() {
        return (
            <div className='popup'>
                <div className='popup_inner'>
                    <h2>장소(업체)명 : {this.props.placeName}</h2>
                    <h3>카테고리 : {this.props.categoryName}</h3>
                    <h3>전화번호 : {this.props.phone}</h3>
                    <h3>상세페이지 : <a href={`${this.props.placeUrl}`} target="_blank">확인하기(클릭)</a></h3>
                    <h3>지번주소 : {this.props.addressName}</h3>
                    <h3>도로명주소 : {this.props.roadAddressName}</h3>
                    <h2>지도보기 : <a href={`https://map.kakao.com/link/to/${this.props.id}`} target="_blank">바로가기(클릭)</a>
                    </h2>
                </div>
            </div>
        );
    }
}


class KeywordHistory extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', data: []};
        CallApiGet.getMyKeywordHistory(this);
    }

    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    handleRefresh = (event) => {
        CallApiGet.getMyKeywordHistory(this);
    }

    render() {
        return (
            <div>
                [ 키워드 히스토리 ]
                <span><button onClick={this.handleRefresh}> 새로고침 </button></span>
                <KeywordHistoryResult data={this.state.data}/>
            </div>
        );
    }
}

class KeywordHistoryResult extends React.Component {
    render() {
        return (
            <div>
                <BootstrapTable data={this.props.data}>
                    <TableHeaderColumn isKey dataField='keyword'
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="7%"
                                       tdStyle={
                                           {backgroundColor: '#f2f2f2'}}>검색키워드
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='lastSearchDate'
                                       dataAlign='left'
                                       headerAlign="center"
                                       width="1%"
                                       tdStyle={
                                           {backgroundColor: '#e6f2ff'}}>최근조회일
                    </TableHeaderColumn>
                </BootstrapTable>
            </div>
        );
    }

}

class PopulateKeyword extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', data: []};

        CallApiGet.getPopulateKeyword(this);
    }

    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    handleRefresh = (event) => {
        CallApiGet.getPopulateKeyword(this);
    }

    handleSubmit = (event) => {
        event.preventDefault();
    }

    render() {
        return (
            <div>
                [ 인기 검색어 TOP 10 ]
                <span><button onClick={this.handleRefresh}> 새로고침 </button></span>
                <PopulateKeywordResult data={this.state.data}/>
            </div>
        );
    }
}

class PopulateKeywordResult extends React.Component {
    render() {
        return (
            <div>
                <BootstrapTable data={this.props.data}>
                    <TableHeaderColumn isKey dataField='keyword'
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="7%"
                                       tdStyle={
                                           {backgroundColor: '#f2f2f2'}}>인기키워드
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='count'
                                       dataAlign='left'
                                       headerAlign="center"
                                       width="1%"
                                       tdStyle={
                                           {backgroundColor: '#e6f2ff'}}>조회수
                    </TableHeaderColumn>
                </BootstrapTable>

                {<div><Logout></Logout></div>}
            </div>
        );
    }
}

class Logout extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', data: []};
    }

    handleLogout = (event) => {
        CallApiGet.callLogout()
            .then(data => {
                if (data === "success") {
                    window.location.replace("/");
                }
            });
    }

    render() {
        return (
            <div>
                <button onClick={this.handleLogout}>로그아웃</button>
            </div>
        );
    }
}

export default LocationSearch;