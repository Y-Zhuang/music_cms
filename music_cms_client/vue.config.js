module.exports = {
	devServer: {
		port: 8091,
		proxy: {
			"/api": {
				target: "http://localhost:8090",
				changeOrigin: true,
				secure: false,
				pathRewrite: {
					"^/api": "/"
				}
			}
		}
	}
};
