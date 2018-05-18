/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { PrescriptionComponent } from '../../../../../../main/webapp/app/entities/prescription/prescription.component';
import { PrescriptionService } from '../../../../../../main/webapp/app/entities/prescription/prescription.service';
import { Prescription } from '../../../../../../main/webapp/app/entities/prescription/prescription.model';

describe('Component Tests', () => {

    describe('Prescription Management Component', () => {
        let comp: PrescriptionComponent;
        let fixture: ComponentFixture<PrescriptionComponent>;
        let service: PrescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [PrescriptionComponent],
                providers: [
                    PrescriptionService
                ]
            })
            .overrideTemplate(PrescriptionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrescriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrescriptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Prescription(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.prescriptions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
