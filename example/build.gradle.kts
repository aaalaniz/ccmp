plugins {
    id("xyz.alaniz.aaron.ccmp")
}

ccmp {
    compose = true
    circuit = true
    parcelize = true
}

android {
    namespace = "xyz.alaniz.aaron.example"
}
