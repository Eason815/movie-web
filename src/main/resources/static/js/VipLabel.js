
    function checkMembership() {
        fetch('/checkMembership', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                if (!data.isVip) {
                    document.getElementById('noVip-tag').style.display = 'block';
                }
                else{
                    document.getElementById('vip-tag').style.display = 'block';
                }
            });
    }

    checkMembership();
