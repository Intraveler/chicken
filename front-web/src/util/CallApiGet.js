import axios from 'axios';

export function getLocationList(object, keyword, page) {

    axios.get('/keyword/location', {
        params: {
            keyword: keyword,
            page: page
        }
    })
        .then(function (response) {
            console.log(response);
            object.setState({data: response.data.list, total_count: response.data.total_count, is_end: response.data.is_end});

        })

        .catch(function (error) {
            console.log(error);
        });
}

export function getMyKeywordHistory(object) {

    axios.get('/keyword/mykeyword')
        .then(function (response) {
            console.log(response);
            object.setState({data: response.data});
        })

        .catch(function (error) {
            console.log(error);
        });
}

export function getPopulateKeyword(object) {

    axios.get('/keyword/populate')
        .then(function (response) {
            console.log(response);
            object.setState({data: response.data.content});
        })

        .catch(function (error) {
            console.log(error);
        });
}

export function callLogout() {

    return axios.get('/member/logout')
        .then(function (response) {
            console.log("then");
            return "success";
        })

        .catch(function (error) {
            console.log("catch");
            console.log(error);
            return "error";
        });
}