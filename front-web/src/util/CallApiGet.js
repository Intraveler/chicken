import axios from 'axios';

export function getLocationList(object, keyword, page) {

    return axios.get('/keyword/location', {
        params: {
            keyword: keyword,
            page: page
        }
    })
        .then(function (response) {
            object.setState({
                data: response.data.list,
                total_count: response.data.total_count,
                is_end: response.data.is_end
            });
            return "success";
        })
        .catch(function (error) {
            alert("[E01] 통신에 문제가 발생했습니다.");
            return "error";
        });
}

export function getMyKeywordHistory(object) {
    return axios.get('/keyword/mykeyword')
        .then(function (response) {
            object.setState({data: response.data});
            return "success";
        })
        .catch(function (error) {
            alert("[E02] 통신에 문제가 발생했습니다.");
            return "error";
        });
}

export function getPopulateKeyword(object) {
    return axios.get('/keyword/populate')
        .then(function (response) {
            object.setState({data: response.data.content});
            return "success";
        })
        .catch(function (error) {
            alert("[E03] 통신에 문제가 발생했습니다.");
            return "error";
        });
}

export function callLogout() {
    return axios.get('/member/logout')
        .then(function (response) {
            console.log("then");
            return "success";
        })

        .catch(function (error) {
            alert("[E04] 통신에 문제가 발생했습니다.");
            return "error";
        });
}