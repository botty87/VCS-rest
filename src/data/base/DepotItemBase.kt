package com.vcs.data.base

interface DepotItemBase {
    var address: String
    var latitude: Double
    var longitude: Double
    var name: String
    var openingHours: String
    var shareDepot: Boolean
}