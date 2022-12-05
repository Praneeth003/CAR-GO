import re
def snake_to_camel(word):    
    k = word.split('_')
    return k[0] + ''.join(x.capitalize() or '_' for x in (k[1:]))

input_ = '''
    c_location_id int NOT NULL AUTO_INCREMENT,
    c_location_name varchar(30) NOT NULL, 
    c_location_gps varchar(500)  DEFAULT NULL,
    c_location_state_name varchar(30) NOT NULL, 
    c_location_country_name varchar(30) NOT NULL, 
    c_location_status tinyint(1) NOT NULL DEFAULT '1',
    '''

def transformWord(word):
    k = word.split(' ')
    if(len(k)) < 2:
        return
    # print(k)
    finalString = ''
    varName = k[0].replace('c_','')
    fieldName = k[1].lstrip(' ')
    # print(varName)
    if(k[1] == 'int'):
        finalString = 'Integer ' + snake_to_camel(varName)
    if(k[1] == 'double'):
        finalString = 'Double ' + snake_to_camel(varName)
    if (k[1].find('varchar')) >= 0 or (k[1].find('text')) >= 0:
        finalString = 'String ' + snake_to_camel(varName)
    if (k[1].find('tinyint')) >= 0:
        finalString = 'Boolean ' + snake_to_camel(varName)
    print(finalString + ";")

def transformWord2(word):
    k = word.split(' ')
    if(len(k)) < 2:
        return
    # print(k)
    finalString = ''
    varName = k[0].replace('c_','')
    fieldName = k[1].lstrip(' ')
    finalString = 'IN  in_' +  varName + " " + fieldName 
    # print(varName)
    # if(k[1] == 'int'):
    #     finalString = 'Integer ' + snake_to_camel(varName)
    # if(k[1] == 'double'):
    #     finalString = 'Double ' + snake_to_camel(varName)
    # if (k[1].find('varchar')) >= 0 or (k[1].find('text')) >= 0:
    #     finalString = 'String ' + snake_to_camel(varName)
    # if (k[1].find('tinyint')) >= 0:
    #     finalString = 'Boolean ' + snake_to_camel(varName)
    print(finalString + ";")

def transformWord3(word):
    k = word.split(' ')
    if(len(k)) < 2:
        return
    return  "pickup_location." + k[0] + " as pickup_location_" + k[0].replace('c_location_','')
    # return k[0]
    # print(k)
    # finalString = ''
    # varName = k[0].replace('c_','')
    # fieldName = k[1].lstrip(' ')
    # finalString = 'IN  in_' +  varName + " " + fieldName 
    # print(varName)
    # if(k[1] == 'int'):
    #     finalString = 'Integer ' + snake_to_camel(varName)
    # if(k[1] == 'double'):
    #     finalString = 'Double ' + snake_to_camel(varName)
    # if (k[1].find('varchar')) >= 0 or (k[1].find('text')) >= 0:
    #     finalString = 'String ' + snake_to_camel(varName)
    # if (k[1].find('tinyint')) >= 0:
    #     finalString = 'Boolean ' + snake_to_camel(varName)
    # print(finalString + ";")


# k = input_.split(',')
# for word in k:
#     word = word.lstrip('\n\t').strip('\n    ').strip('\t')
#     # if(word )
#     transformWord2(word)

k = input_.split(',')
m = []
for word in k:
    word = word.lstrip('\n\t').strip('\n    ').strip('\t')
    # if(word )
    t = transformWord3(word)
    if t is not None :
        m.append(t)
    
print(','.join(m))
