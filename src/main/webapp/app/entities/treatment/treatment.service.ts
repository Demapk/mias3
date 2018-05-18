import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Treatment } from './treatment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Treatment>;

@Injectable()
export class TreatmentService {

    private resourceUrl =  SERVER_API_URL + 'api/treatments';

    constructor(private http: HttpClient) { }

    create(treatment: Treatment): Observable<EntityResponseType> {
        const copy = this.convert(treatment);
        return this.http.post<Treatment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(treatment: Treatment): Observable<EntityResponseType> {
        const copy = this.convert(treatment);
        return this.http.put<Treatment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Treatment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Treatment[]>> {
        const options = createRequestOption(req);
        return this.http.get<Treatment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Treatment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Treatment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Treatment[]>): HttpResponse<Treatment[]> {
        const jsonResponse: Treatment[] = res.body;
        const body: Treatment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Treatment.
     */
    private convertItemFromServer(treatment: Treatment): Treatment {
        const copy: Treatment = Object.assign({}, treatment);
        return copy;
    }

    /**
     * Convert a Treatment to a JSON which can be sent to the server.
     */
    private convert(treatment: Treatment): Treatment {
        const copy: Treatment = Object.assign({}, treatment);
        return copy;
    }
}
