//
//  SearchBar.swift
//  MoviesBudget
//
//  Created by admin on 2022. 01. 13..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct SearchBar: View {
    @State var query: String
    
    var onChange: (String) -> Void
    var onSearch: () -> Void
    @State private var isEditing = false
 
    var body: some View {
        HStack {
 
            TextField("Search ...", text: $query)
                .padding(7)
                .padding(.horizontal, 25)
                .background(Color(.systemGray6))
                .onChange(of: query, perform: onChange)
                .cornerRadius(8)
                .padding(.horizontal, 10)
                .onTapGesture {
                    self.isEditing = true
                }
 
            if isEditing {
                Button(action: {
                    self.isEditing = false
                    onSearch()
                }) {
                    Text("Search")
                }
                .padding(.trailing, 10)
                .transition(.move(edge: .trailing))
                .animation(.default)
            }
        }
    }
}
