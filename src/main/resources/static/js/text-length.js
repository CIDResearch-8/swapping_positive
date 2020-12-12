var app = new Vue({
    el: '#text-control',
    data() {
        return {
            isButtonDisabled: false,
            inputText: "",
            msg: "Welcome to Your Vue.js App"
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
                this.msg = response.data;
                console.log('getting user id');
            })
            .catch(err => {
                console.log('get error');
        });
    },
    methods: {
        submit: function() {
            axios
                .post('/rest-api/comment/post', {
                    inputText: this.inputText,
                    userId: this.msg
                })
                .then(() => {
                    console.log("success submit");
                })
                .catch(err => {
                    console.log('submit error');
                });
        }
    }
})