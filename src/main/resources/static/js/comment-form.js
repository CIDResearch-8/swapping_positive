var app = new Vue({
    el: '#text-control',
    data() {
        return {
            isButtonDisabled: false,
            inputText: "",
            userId: ""
        }
    },
    watch: {
        inputText: function(inputText) {
            this.inputText = inputText;
        }
    },
    computed: {
        isEmpty() {
            return this.inputText.length <= 0;
        }
    },
    mounted() {
        axios
            .get('/rest-api/login-user-id/get')
            .then(response => {
                this.userId = response.data;
                console.log('getting user id');
            })
            .catch(err => {
                console.log('get error');
        });
    },
    methods: {
        submit: function() {
            if (!this.isEmpty)  {
                axios
                    .post('/rest-api/comment/post', {
                        inputText: this.inputText,
                        userId: this.userId
                    })
                    .then(() => {
                        console.log("success submit");
                        this.inputText = '';
                    })
                    .catch(err => {
                        console.log('submit error');
                    });
            }
        }
    }
})