import { ElLoading } from "element-plus";

let loadingRequestCount = 0
let loading

function startLoading() {
    loading = ElLoading.service({
        lock: true,
        text: "加载中......",
        background: 'rgba(255,255,255,0.7)'
    })
}

function stopLoading() {
    loading.close()
}

const tryCloseLoading = () => {
    if (loadingRequestCount === 0) {
        stopLoading()
    }
}

export function showFullScreenLoading() {
    if (loadingRequestCount === 0) {
        startLoading()
    }
    loadingRequestCount++
}

export function tryHideFullScreenLoading() {
    if (loadingRequestCount <= 0) {
        return
    }
    loadingRequestCount--
    if (loadingRequestCount === 0) {
        tryCloseLoading()
    }
}