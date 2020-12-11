var app = new Vue({
    el: '#text-control',
    data: {
        isButtonDisabled: false,
        inputText: []
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
    }
})