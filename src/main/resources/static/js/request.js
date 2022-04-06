const request = {
    post: function (url, param) {
        let header = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
        return new Promise((resolve, reject) => {
            $.ajax({
                url: url,
                method: "POST",
                data: JSON.stringify(param),
                header: header,
                contentType: 'application/json',
                dataType: 'json',
                success: function (response) {
                    if (response.code === '000') {
                        resolve(response.data)
                    } else {
                        reject(response.message)
                    }
                },
                error: function (response) {
                    reject(response)
                }
            })
        })
    },

    get: function (url, param) {
        return new Promise(resolve => {
            uni.request({
                url: `${baseUrl}${url}`,
                method: "GET",
                timeout: 5000,
                data: param,
                success: (response) => {
                    if (response.statusCode === 200) {
                        let data = response.data
                        if (data.code === '000') {
                            resolve(data)
                        } else {
                            return Promise.reject(data.message)
                        }
                    } else {
                        return Promise.reject()
                    }
                },
                fail: (err) => {
                    return Promise.reject(err)
                }
            })
        })
    },
}